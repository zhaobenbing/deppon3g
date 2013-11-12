package com.deppon.server.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import oracle.jdbc.internal.OracleTypes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.deppon.common.beans.Order;
import com.deppon.common.beans.PriceBean;
import com.deppon.common.beans.TrackInfo;
import com.deppon.common.beans.TransPropertyEnum;
import com.deppon.common.util.HibernateSessionFactory;
import com.deppon.server.dao.intfce.IOrderDAO;

/**
 * @功能描述：定单DAO现实
 * @author 赵本兵
 * @创建时间：2011-10-13
 */
public class OrderDAOImpl implements IOrderDAO {
	@Override
	public boolean addOrder(Order order) {
		Session session = null;
		Transaction txt = null;
		try {
			session = HibernateSessionFactory.getSession();
			// 开启事务
			txt = session.beginTransaction();
			session.save(order);
			// 提交事务
			txt.commit();
		} catch (Exception e) {
			e.printStackTrace();
			txt.rollback();
		} finally {
			if (session != null) {
				HibernateSessionFactory.closeSession();
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order findOrderById(String orderId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dp_3g_dept where D_ORDERNUMBER =:orderId ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"orderId", orderId);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Order order = new Order();
		Object[] obj = (Object[]) list.get(0);
		order
				.setId(obj[0] == null ? null : Integer.parseInt(obj[0]
						.toString()));
		order.setOrderNumber(obj[1] == null ? null : obj[1].toString());
		order.setUserId(obj[2] == null ? null : obj[2].toString());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String temp = obj[3].toString();
		Date date;
		try {
			date = df.parse(temp);
			order.setOrderDate(obj[3] == null ? null : date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		order.setGoodsName(obj[4] == null ? null : obj[4].toString());
		order
				.setState(obj[5] == null ? 0 : Integer.parseInt(obj[5]
						.toString()));
		order.setDeptId(obj[6] == null ? null : obj[6].toString());
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order[] findSomeUserOrders(String userId) {
		System.out.println("-------userId------"+userId);
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dp_3g_order where D_USERID=:userId ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"userId", userId);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Order[] orders = new Order[list.size()];
		for (int i = 0; i < orders.length; i++) {
			Object[] obj = (Object[]) list.get(i);
			orders[i] = new Order();
			orders[i].setId(obj[0] == null ? null : Integer.parseInt(obj[0].toString()));
			orders[i].setOrderNumber(obj[1] == null ? null : obj[1].toString());
			orders[i].setUserId(obj[2] == null ? null : obj[2].toString());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String temp = obj[3].toString();
			Date date;
			try {
				date = df.parse(temp);
				orders[i].setOrderDate(obj[3] == null ? null : date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			orders[i].setGoodsName(obj[4] == null ? null : obj[4].toString());
			orders[i].setState(obj[5] == null ? 0 : Integer.parseInt(obj[5]
					.toString()));
			orders[i].setDeptId(obj[6] == null ? null : obj[6].toString());
 		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
 		return orders;
	}

	@Override
	public void modifyOrder(Order order) {

	}

	// 运单查询
	@Override
	public String findWayBill(String wayBillNumber) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String strUrl = "jdbc:oracle:thin:@192.168.17.201:1521:depponit";
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		TrackInfo track = null;
		String trackInfo = "";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(strUrl, "deppon009",
					"dperp32011");
			CallableStatement proc = null;
			proc = conn.prepareCall("{ call dptrack.get_ows_track(?,?,?,?) }");
			proc.setString(1, wayBillNumber);
			proc.registerOutParameter(2, OracleTypes.VARCHAR);
			proc.registerOutParameter(3, OracleTypes.CURSOR);
			proc.registerOutParameter(4, OracleTypes.CURSOR);
			proc.execute();
			String errorMessage = proc.getString(2);
			if (errorMessage != null) {
				System.out.println("抱歉，系统不能找到您要的单号。");
				return null;
			} else {
				// 基本信息
				rs = (ResultSet) proc.getObject(3);
				while (rs.next()) {
					track = new TrackInfo();
					track.setStatus(getWayBillStatus(Integer.parseInt(rs
							.getString(15))));
					track.setStatusDescription(processSigner(rs.getString(16)));
					track.setArrivecity(rs.getString(4));
					track.setTraType(getTraType(Integer.parseInt(rs
							.getString(10))));
					track.setTakeWay(getTakeType(Integer.parseInt(rs
							.getString(8))));
					track.setGoodsName(rs.getString(11));
					track.setGoodsCount(rs.getString(3));
					track.setGoodsWeight(rs.getString(12));
					track.setGoodsVolume(rs.getString(13));
					// 到达信息takeName,takePerson,takePhone,takeAddress;
					track.setTakeName(rs.getString(6));
					track.setTakePerson(processSigner(rs.getString(9)));
					track.setTakePhone(rs.getString(7));
					track.setTakeAddress(rs.getString(5));
				}
				rs1 = (ResultSet) proc.getObject(4);
				while (rs1.next()) {
					trackInfo += getTrack(rs1);
				}
				System.out.println(trackInfo);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return track.toString() + "%!%" + trackInfo;
	}

	// 获取跟踪信息
	private String getTrack(ResultSet tTrackSet) {
		// 获取交接记录:DateTime lTime, dTime;
		String lTime, dTime, status, lCity, dCity, lDept, dDept, strHtml, strTagHead, strTagEnd, nextStatus;
		strTagHead = "";
		strTagEnd = "@";
		// 上一次到达时间与地点
		@SuppressWarnings("unused")
		String pDTime, pDcity, pDDept, pStatus;
		pDTime = pDcity = pDDept = pStatus = "";

		boolean flag = false;
		try {
			// 移动指针到第一行
			// tTrackSet.next();
			if (tTrackSet.getRow() <= 0) {
				return strHtml = "正在为您安排发货，请您耐心等待!";
			} else {

				strHtml = "";
			}
			for (int i = 0; i < tTrackSet.getRow(); i++) {
				// 取出每行的值
				lTime = tTrackSet.getString("LTIME") == null ? "" : tTrackSet
						.getString("LTIME").toString().trim();
				dTime = tTrackSet.getString("DTIME") == null ? "" : tTrackSet
						.getString("DTIME").toString().trim();
				status = tTrackSet.getString("STATUS") == null ? "" : tTrackSet
						.getString("STATUS").toString().trim();
				lCity = tTrackSet.getString("LCITY") == null ? "" : tTrackSet
						.getString("LCITY").toString().trim();
				dCity = tTrackSet.getString("DCITY") == null ? "" : tTrackSet
						.getString("DCITY").toString().trim();
				lDept = tTrackSet.getString("LDEPT") == null ? "" : tTrackSet
						.getString("LDEPT").toString().trim();
				dDept = tTrackSet.getString("DDEPT") == null ? "" : tTrackSet
						.getString("DDEPT").toString().trim();

				// 取出下一行状态
				if (lTime != "")
					lTime = formatDate(lTime);
				if (dTime != "")
					dTime = formatDate(dTime);
				// lDept = changeDeptName(lDept, lCity);
				// /dDept = changeDeptName(dDept, dCity);

				int ii = i + 1;
				if (ii < tTrackSet.getRow() && tTrackSet.next()) {
					nextStatus = tTrackSet.getString("STATUS") == null ? ""
							: tTrackSet.getString("STATUS").toString().trim();
					tTrackSet.previous();
				} else {
					nextStatus = "-1";
				}
				// 有两类数据,交接单与人工跟踪 status = 0,1 交接单,其它情况人工电话跟踪
				// 在途
				if (status == "0") {
					strHtml += strTagHead + lTime + "		系统跟踪		离开 " + lDept
							+ " 发往 " + dDept + strTagEnd;

				} else if (status == "1") {
					strHtml += strTagHead + lTime + "		系统跟踪		离开 " + lDept
							+ " 发往 " + dDept + strTagEnd;
					if (nextStatus == "0" || nextStatus == "1"
							|| nextStatus == "-1" || nextStatus == "3"
							|| nextStatus.indexOf("4") == 0) {
						strHtml += strTagHead + dTime + "		系统跟踪		已到达 " + dDept
								+ strTagEnd;
					} else {
						pDTime = dTime;
						pDcity = dCity;
						pDDept = dDept;
						pStatus = status;
						flag = true;
					}
				} else if (status == "3") {
					strHtml += strTagHead + lTime + "		提货通知		" + lCity
							+ strTagEnd;
				} else if (status.indexOf("4") == 0) {
					if (status == "4" || status == "40")
						strHtml += strTagHead + lTime + "		正常签收		签收人:" + lCity
								+ strTagEnd;
					else
						strHtml += strTagHead + lTime + "		异常签收		签收人:" + lCity
								+ strTagEnd;

				} else if (status == "21") {
					// 处理一下GPS信息内的多于信息
					if (dCity.indexOf("位置") > 0) {
						dCity = (dCity.length() - 23 > 5) ? dCity.substring(23,
								dCity.length() - 23) : dCity;
					}
					strHtml += strTagHead + dTime + "		" + "		卫星定位" + "		"
							+ dCity + strTagEnd;
					if (nextStatus == "0" || nextStatus == "1"
							|| nextStatus == "-1" || nextStatus == "3")
						flag = true;
				} else {
					strHtml += strTagHead + dTime + "		" + "		人工跟踪" + "		"
							+ dCity + strTagEnd;
					if (nextStatus == "0" || nextStatus == "1"
							|| nextStatus == "-1" || nextStatus == "3")
						flag = true;
				}
				if (pStatus == "1" && flag) {
					strHtml += strTagHead + pDTime + "		系统跟踪		已到达 " + pDDept
							+ strTagEnd;
					flag = false;
					pStatus = "0";
				}
			}
			return strHtml;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getWayBillStatus(int parseInt) {
		switch (parseInt) {
		case 1:
			return "开单";
		case 2:
			return "运输途中";
		case 3:
			return "已到达";
		case 4:
			return "已签收";
		case 5:
			return "已外发";
		case 6:
			return "已做废";
		default:
			return "";
		}
	}

	// 过滤签收人信息
	private String processSigner(String signer) {
		if (signer == null || signer.toString() == "") {
			return "未签收";
		}
		String strSigner = signer.toString().trim();
		for (int i = 0; i < 9; i++) {
			strSigner = strSigner.replace(i + "", "");
			switch (i) {
			case 0:
				strSigner = strSigner.replace("０", "");
				break;
			case 1:
				strSigner = strSigner.replace("１", "");
				break;
			case 2:
				strSigner = strSigner.replace("２", "");
				break;
			case 3:
				strSigner = strSigner.replace("３", "");
				break;
			case 4:
				strSigner = strSigner.replace("４", "");
				break;
			case 5:
				strSigner = strSigner.replace("５", "");
				break;
			case 6:
				strSigner = strSigner.replace("６", "");
				break;
			case 7:
				strSigner = strSigner.replace("７", "");
				break;
			case 8:
				strSigner = strSigner.replace("８", "");
				break;
			case 9:
				strSigner = strSigner.replace("９", "");
				break;
			}
		}
		strSigner = strSigner.replace("/", "");
		strSigner = strSigner.replace("\\", "");
		strSigner = strSigner.replace("／", "");
		strSigner = strSigner.replace("、", "");
		strSigner = strSigner.replace("正常签收!", "");
		strSigner = strSigner.replace("*", "");
		return strSigner;
	}

	private String getTakeType(int parseInt) {
		String strRetText = "";
		switch (parseInt) {
		case 0:
		case 2:
		case 11:
		case 8:
		case 10:
			strRetText = "送货上门（不含上楼）";
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
			strRetText = "自提";
			break;
		case 4:
			strRetText = "机场自提";
			break;
		case 6:
			strRetText = "送货上楼";
			break;
		case 14:
			strRetText = "送货上门";
			break;
		}
		return strRetText;
	}

	private String getTraType(int i) {
		String[] types = { "精准空运", "精准汽运(长)", "精准卡航", "精准城运", "汽运偏线", "精准汽运(短)" };
		switch (i) {
		case 1:
			return types[0];
		case 2:
			return types[1];
		case 3:
			return types[2];
		case 4:
			return types[3];
		case 5:
			return types[4];
		case 6:
			return types[5];
		default:
			return null;
		}
	}

	private String formatDate(String string) {
		if (string == null || "".equals(string)) {
			return null;
		} else {
			String[] data = string.split("\\s+");
			String[] temp = data[0].split("-");
			String year = temp[0];
			String month = temp[1];
			String date = temp[2];
			String hour = data[1].split(":")[0];
			String minute = data[1].split(":")[1];
			return year + "年" + month + "月" + date + "日" + hour + "点" + minute
					+ "分";
		}
	}

	@Override
	public PriceBean priceSearch(String startCityId, String arriveCityId,
			int paras_TransProperty, double paras_GoodsWeight,
			double paras_GoodsVolume) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String strUrl = "jdbc:oracle:thin:@192.168.17.201:1521:depponit";
		Connection conn = null;
		PriceBean objOnlinePrice = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(strUrl, "deppon009",
					"dperp32011");
			CallableStatement proc = null;
			proc = conn.prepareCall("{ call get_price(?,?,?,?,?,"
					+ "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?,"
					+ "?,?,?,?,?,?) }");
			// 输入参数
			proc.setString(1, startCityId);
			proc.setString(2, arriveCityId);
			proc.setInt(3, paras_TransProperty);
			proc.setDouble(4, paras_GoodsWeight);
			proc.setDouble(5, paras_GoodsVolume);
			// ##输出参数
			// 轻货价格
			proc.registerOutParameter(6, OracleTypes.DOUBLE);
			// 重货价格
			proc.registerOutParameter(7, OracleTypes.DOUBLE);
			// 最低一票
			proc.registerOutParameter(8, OracleTypes.DOUBLE);
			// 时效
			proc.registerOutParameter(9, OracleTypes.NVARCHAR);
			// 到达营业部所需天数
			proc.registerOutParameter(10, OracleTypes.INTEGER);
			// 到达营业部时间点
			proc.registerOutParameter(11, OracleTypes.VARCHAR);
			// 派送天数
			proc.registerOutParameter(12, OracleTypes.INTEGER);
			// 派送时间点
			proc.registerOutParameter(13, OracleTypes.VARCHAR);
			// 交通运输费
			proc.registerOutParameter(14, OracleTypes.DOUBLE);
			// 重货标志
			proc.registerOutParameter(15, OracleTypes.INTEGER);
			// 空运早班
			proc.registerOutParameter(16, OracleTypes.DOUBLE);
			// 空运中班
			proc.registerOutParameter(17, OracleTypes.DOUBLE);
			// 空运晚班
			proc.registerOutParameter(18, OracleTypes.DOUBLE);
			// 空运班期
			proc.registerOutParameter(19, OracleTypes.NVARCHAR);
			// 空运价格信息
			proc.registerOutParameter(20, OracleTypes.NVARCHAR);
			// 返回信息
			proc.registerOutParameter(21, OracleTypes.NVARCHAR);
			// 空运早班价格
			proc.registerOutParameter(22, OracleTypes.DOUBLE);
			// 空运中班价格
			proc.registerOutParameter(23, OracleTypes.DOUBLE);
			// 空运晚班价格
			proc.registerOutParameter(24, OracleTypes.DOUBLE);
			// 上门接货价格
			proc.registerOutParameter(25, OracleTypes.INTEGER);
			// 上门接货最低一票
			proc.registerOutParameter(26, OracleTypes.DOUBLE);

			proc.execute();
				
			String errorMessage = proc.getString(21);
			if (errorMessage != null || "unknown error".equals(errorMessage)) {
				System.out.println("==errorMessage=="+errorMessage);
				System.out.println("抱歉，存储过程发生异常。");
				return null;
			} else {
				// 基本信息

				if (!"no data".equals(errorMessage)) {
					// 重货单价 + 轻货单价 + 最低收费
					objOnlinePrice = new PriceBean();
					String lprice = proc.getString(6) == null?"0":proc.getString(6);
					// 轻货价格6
					objOnlinePrice.setLightPrice(Double.parseDouble(lprice));
					
					String wprice = proc.getString(7) == null?"0":proc.getString(7);
					// 重货价格7
					objOnlinePrice.setWeightPrice(Double.parseDouble(wprice));
					
					String mprice =  proc.getString(8) == null?"0":proc.getString(8);
					 // 最低一票8
					objOnlinePrice.setMinPrice(Double.parseDouble(mprice));
					
					String worktime = proc.getString(9) == null?"":proc.getString(9);
					// 时效 	9
					objOnlinePrice.setWorkTime(worktime.toString().trim());
					
					String ardates = proc.getString(10) == null?"0":proc.getString(10);					
					// 到达营业部所需天数 10
					objOnlinePrice.setArrDates(Integer.parseInt(ardates));
					
					String ardap = proc.getString(11) == null?"":proc.getString(11);			
					// 到达营业部时间点11
					objOnlinePrice.setArrTimePoint(ardap);
					// *********************************************************************
					String sendda = proc.getString(12) == null?"0":proc.getString(12);
					// 派送天数12
					objOnlinePrice.setSendDates(Integer.parseInt(sendda));
					
					String sendp = proc.getString(13) == null?"":proc.getString(13);
					 // 派送时间点13
					objOnlinePrice.setSendTimePoint(sendp);
					
					String total = proc.getString(14) == null?"0":proc.getString(14);
					// 交通运输费 14
					objOnlinePrice.setTotalAmount(Double.parseDouble(total));
					
					String wg = proc.getString(15) == null?"0":proc.getString(15);
					// 重货还是轻货？15
					objOnlinePrice.setWeightGoodFlag(Integer.parseInt(wg));
					// **********************空运班***********************************************
					String morning = proc.getString(16) == null?"0":proc.getString(16);
					// 空运早班16
					objOnlinePrice.setMorning(Double.parseDouble(morning));
					
					String non = proc.getString(17) == null?"0":proc.getString(17);
					 // 空运中班17
					objOnlinePrice.setNoon(Double.parseDouble(non));
					
					String n = proc.getString(18) == null?"0":proc.getString(18);
					// 空运晚班18
					objOnlinePrice.setNight(Double.parseDouble(n));
					
					String schedule = proc.getString(19) == null?"":proc.getString(19);
					//空运班期19
					objOnlinePrice.setSchedule(schedule);
					
					String airpr = proc.getString(20) == null?"":proc.getString(20);
					// 空运价格信息20
					objOnlinePrice.setAirPriceRemark(airpr);
					//****************此处省略21************************************
					String airmp = proc.getString(22) == null?"0":proc.getString(22);
					// 空运早班价格22
					objOnlinePrice.setAirwayMorningPrice(Double.parseDouble(airmp));
					
					String airnp = proc.getString(23) == null?"0":proc.getString(23);
					// 空运中班价格23
					objOnlinePrice.setAirwayNoonPrice(Double.parseDouble(airnp));
					
					String airnnip = proc.getString(24) == null?0+"":proc.getString(24);
					 // 空运晚班价格 24
					objOnlinePrice.setAirwayNightPrice(Double.parseDouble(airnnip));
					
					String gfh = proc.getString(25) == null?"0":proc.getString(25);
					// 上门接货价25
					objOnlinePrice.setIsGetFromHome(Integer.parseInt(gfh));
					String pmp = proc.getString(26) == null?0+"":proc.getString(26);
					// 上门接货最低一票26
					objOnlinePrice.setPickUpMinPrice(Double.parseDouble(pmp));
					// **********************************************************************			
					objOnlinePrice.setTransProperty(TransPropertyEnum.getEnum(paras_TransProperty));
				} else {
					return null;
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return objOnlinePrice;
	}
	//查询某个用户的未受理或受理或取消定单 0:表示未受理 1:表示已受理 -1:表示该订单已经取消
	@SuppressWarnings("unchecked")
	@Override
	public Order[] findSomeUntrearedOrders(String userId, int status) {
 		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dp_3g_order where D_USERID=:userId and D_STATE=:state");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"userId", userId).setParameter("state", status);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Order[] orders = new Order[list.size()];
		for (int i = 0; i < orders.length; i++) {
			Object[] obj = (Object[]) list.get(i);
			orders[i] = new Order();
			orders[i].setId(obj[0] == null ? null : Integer.parseInt(obj[0].toString()));
			orders[i].setOrderNumber(obj[1] == null ? null : obj[1].toString());
			orders[i].setUserId(obj[2] == null ? null : obj[2].toString());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String temp = obj[3].toString();
			Date date;
			try {
				date = df.parse(temp);
				orders[i].setOrderDate(obj[3] == null ? null : date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			orders[i].setGoodsName(obj[4] == null ? null : obj[4].toString());
			orders[i].setState(obj[5] == null ? 0 : Integer.parseInt(obj[5]
					.toString()));
			orders[i].setDeptId(obj[6] == null ? null : obj[6].toString());
 		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
 		return orders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order[] findLastOrder(String userId, int start, int end) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.d_id,t1.d_ordernumber,t1.d_userid,t1.d_orderdate,t1.d_goodsname,t1.d_state,t1.d_deptid from  ");
		sql.append(" (select rownum r1,t.* from ");
		sql.append(" (select rownum r, o.* from dp_3g_order o  where d_userid=:userId order by d_orderdate desc)t)t1 ");
		sql.append(" where t1.r1>=:start and t1.r1<=:end ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"userId", userId).setParameter("start", start).setParameter("end", end);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Order[] orders = new Order[list.size()];
		for (int i = 0; i < orders.length; i++) {
			Object[] obj = (Object[]) list.get(i);
			orders[i] = new Order();
			orders[i].setId(obj[0] == null ? null : Integer.parseInt(obj[0].toString()));
			orders[i].setOrderNumber(obj[1] == null ? null : obj[1].toString());
			orders[i].setUserId(obj[2] == null ? null : obj[2].toString());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String temp = obj[3].toString();
			Date date;
			try {
				date = df.parse(temp);
				orders[i].setOrderDate(obj[3] == null ? null : date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			orders[i].setGoodsName(obj[4] == null ? null : obj[4].toString());
			orders[i].setState(obj[5] == null ? 0 : Integer.parseInt(obj[5]
					.toString()));
			orders[i].setDeptId(obj[6] == null ? null : obj[6].toString());
 		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
 		return orders;
	}
	@Override
	public boolean cancleOrder(String orderNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("  update dp_3g_order set d_state = '-1' where d_ordernumber=:orderNumber  ");
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(sql.toString()).setParameter("orderNumber", orderNumber);
		 int isUpdate = query.executeUpdate();
		 session.getTransaction().commit();
		 if(isUpdate>0){
			 return true;
		 }
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order[] findCancledOrder(String userId, int status) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select * from dp_3g_order where d_state=:status and d_userId =:userId  ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"status", status).setParameter("userId", userId);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Order[] orders = new Order[list.size()];
		for (int i = 0; i < orders.length; i++) {
			Object[] obj = (Object[]) list.get(i);
			orders[i] = new Order();
			orders[i].setId(obj[0] == null ? null : Integer.parseInt(obj[0].toString()));
			orders[i].setOrderNumber(obj[1] == null ? null : obj[1].toString());
			orders[i].setUserId(obj[2] == null ? null : obj[2].toString());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String temp = obj[3].toString();
			Date date;
			try {
				date = df.parse(temp);
				orders[i].setOrderDate(obj[3] == null ? null : date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			orders[i].setGoodsName(obj[4] == null ? null : obj[4].toString());
			orders[i].setState(obj[5] == null ? 0 : Integer.parseInt(obj[5]
					.toString()));
			orders[i].setDeptId(obj[6] == null ? null : obj[6].toString());
 		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
 		return orders;
	}
}

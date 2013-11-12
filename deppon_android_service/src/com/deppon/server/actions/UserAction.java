package com.deppon.server.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Province;
import com.deppon.common.beans.User;
import com.deppon.common.util.MD5Util;
import com.deppon.common.util.StatusCode;
import com.deppon.server.services.impl.ServiceImpl;
import com.deppon.server.services.intfce.IService;
 

public class UserAction extends BaseAction {
	private static final long serialVersionUID = 4120242067985437437L;
	private HttpServletRequest request;
	private HttpServletResponse response;
    private IService service = new ServiceImpl();
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		super.setServletRequest(arg0);
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		super.setServletResponse(arg0);
	}
	private void setCharacter(){
  		try {
 			this.response.setCharacterEncoding("utf-8");
 	 		this.response.setContentType("text/html;charset=utf-8");
			this.request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
		}
	}

	/**
	 * 登陆
	 */
 	public void login() {
 		setCharacter();
		String email = this.request.getParameter("email");
		String password = this.request.getParameter("password");
		String encrypt = MD5Util.getMD5Encoding(password);
		User user = null;
		
 		try {
 			user = service.getUserService().login(email,encrypt);
			if (user!=null&&user.getEmail()!=null) {
				System.out.println(user.getEmail());
				System.out.println(user.getPassword());
				System.out.println("userJSON---------->"+user.toString());
				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+user.toString());
				service.getDeptService().findAllProvice();
 			} else {
				this.response.getWriter().write(StatusCode.STATUS_UNEXIST+"#"+StatusCode.EXECPTION);
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册
	 */
	@SuppressWarnings("static-access")
	public void regist() {
		setCharacter();
		String jsonStr = (String)this.request.getParameter("RegistInfo");
		User user = new User();
		JSONObject json = new JSONObject().fromObject(jsonStr);
		String encrypt = MD5Util.getMD5Encoding(json.getString("password"));
		user.setEmail(json.getString("email"));
		user.setTelephone(json.getString("telephone"));
		user.setPassword(encrypt);
		user.setSlstatus(0);
  		System.out.println("tempStr--------->"+json.toString());
		try {
			user.setRegiterTime(new Date());
			user.setLastUpdatetime(new Date());
			//已被注册
			if(service.getUserService().emailExisted(user.getEmail())){
				this.response.getWriter().write(StatusCode.STATUS_EXISTED+"#"+StatusCode.STATUS_EXISTED);
				System.out.println("该Email已被注册");
				return ;
			}else{
				if (service.getUserService().regist(user)) {
	 				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+"注册成功");
	 				System.out.println("注册成功");
	 			} else {
	 				this.response.getWriter().write(StatusCode.STATUS_UNEXIST+"#"+StatusCode.EXECPTION);
					System.out.println("注册失败");
	 			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	//修改密码
	public void modifyPwd(){
		setCharacter();
		System.out.println("==modifyPwd method==");
		String userId = this.request.getParameter("currUserId");
		String oldpwd = this.request.getParameter("oldpwd");
		String newpwd = this.request.getParameter("newpwd");
		System.out.println("==oldpwd=="+oldpwd);
		System.out.println("==newpwd=="+newpwd);
		try {
			String encryptold = MD5Util.getMD5Encoding(oldpwd);
			String encryptnew = MD5Util.getMD5Encoding(newpwd);
			boolean isModify = service.getUserService().modifyPassword(userId, encryptold, encryptnew);
			if(isModify){
				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+"modifyPassword success");
				System.out.println("修改密码成功");
			}else{
				this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"modifyPassword fail");
				System.out.println("修改密码失败");
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}
	}
	/**
	 * webView 实现
	 * 个人资料 加载省份
	 */
	public void loadProvinces(){
		setCharacter();
		System.out.println("====struts2 ajax====");
		List<Province> list = service.getProvinceService().findAllProvinces();
		StringBuilder sb = new StringBuilder();
 		for (Province province : list) {
 			sb.append(province.getProvinceOldId()).append("#").
 			append(province.getProvinceOldName());
 			sb.append(",");
 		}
 		sb.replace(sb.lastIndexOf(",") ,sb.length(), "");
 		try {
			if(list.size()>0){
				this.response.getWriter().println(sb.toString());
			}else{
				this.response.getWriter().println("notProvicnes");
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}finally{
			try {
				this.response.getWriter().flush();
				this.response.getWriter().close();
			} catch (IOException e) {
 				e.printStackTrace();
			}
		}
	}
	/**
	 * webView 实现
	 * 个人资料 加载城市
	 */
	public void loadCitys(){
		setCharacter();
		String proId = this.request.getParameter("provinceId").trim();
		if(proId!=null){
			System.out.println("proId===="+proId);
		}
		List<City> list = service.getCityService().findCityByProvinceId(proId);
		StringBuilder sb = new StringBuilder();
 		for (City c : list) {
 			sb.append(c.getCityId()).append("#").
 			append(c.getCityOldName());
 			sb.append(",");
 		}
 		sb.replace(sb.lastIndexOf(",") ,sb.length(), "");
 		try {
			if(list.size()>0){
				this.response.getWriter().println(sb.toString());
			}else{
				this.response.getWriter().println("notCitys");
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}finally{
			try {
				this.response.getWriter().flush();
				this.response.getWriter().close();
			} catch (IOException e) {
 				e.printStackTrace();
			}
		}
	}
	/**
	 * webView 实现
	 * 保存个人资料
	 */
	@SuppressWarnings("static-access")
	public void SaveBaseInfo(){
		setCharacter();
		String persondatum = this.request.getParameter("PersonDatum");
		JSONObject json = new JSONObject().fromObject(persondatum);
		User user  = new User();
		user.setId(json.getInt("id"));
		user.setName(json.getString("name"));
		user.setTelephone(json.getString("telephone"));
		String mobilephone = json.getString("mobilephone");
		if(mobilephone!=null){
			user.setMobilephone(mobilephone);
		}else{
			user.setMobilephone("without mobilephone ");
		}
		user.setProvince(json.getString("province"));
		user.setCity(json.getString("city"));
		user.setAddress(json.getString("address"));
		boolean isSave = service.getUserService().modifyUserInfo(user);
		System.out.println("====isSave===>"+isSave);
		try {
			if(isSave){
				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+"savepersonInfo success");
				System.out.println("保存信息成功");
			}else{
				this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"savepersonInfo fail");
				System.out.println("保存信息失败");
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}
	}
}

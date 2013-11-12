	function getXmlRequest() {
		var xmlHttp = null;
		if ((typeof xmlHttp) != 'undefined') {
			xmlHttp = new XMLHttpRequest();
		} else {
			xmlHttp = new ActiveXobject('Microsoft.XMLHttp');
		}
		return xmlHttp;
	}

	function loadprovinces() {
		var sel = document.getElementById("province");
		var xmlReq = getXmlRequest();
		var url = "loadprovinces.action";
		xmlReq.open("post", url, true);
		xmlReq.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlReq.onreadystatechange = function() {
			if (xmlReq.readyState == 4) {
				if (xmlReq.status == 200) {
					var resText = xmlReq.responseText;
					var arr = resText.split(",");
					for ( var i = 0; i < arr.length; i++) {
						var temp = arr[i].split("#");
						var op = document.createElement("OPTION");
						op.value = temp[0];
						if (navigator.userAgent.indexOf('Firefox') >= 0) {
							op.textContent = temp[1];
						} else {
							op.innerText = temp[1];
						}
						sel.appendChild(op);
					}
				}
			}
		}
		xmlReq.send();
	}

	function loadCitys(obj) {
		var proId = obj.options[obj.selectedIndex].value;
		var selec = document.getElementById("city");
		selec.options.length = 1;
		var xmlReq = getXmlRequest();
		var url = "loadcitys.action?";
		xmlReq.open("post", url, true);
		xmlReq.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlReq.onreadystatechange = function() {
			if (xmlReq.readyState == 4) {
				if (xmlReq.status == 200) {
					var resText = xmlReq.responseText;
					var arr = resText.split(",");
					for ( var i = 0; i < arr.length; i++) {
						var temp = arr[i].split("#");
						var op = document.createElement("OPTION");
						op.value = temp[0];
						if (navigator.userAgent.indexOf('Firefox') >= 0) {
							op.textContent = temp[1];
						} else {
							op.innerText = temp[1];
						}
						selec.appendChild(op);
					}
				}
			}
		}
		xmlReq.send("provinceId=" + proId);
	}
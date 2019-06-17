//setTab --add by Neal 2-28
function setTab(name, cursel, wMenu) {
	var defaultHref = path('/index.html');
	var defaultInnerText = '';
	var olis = Ext.get('mainNav').dom.getElementsByTagName('li');
	var subMenus = Ext.get('subNav').dom.getElementsByTagName('dl');
	for (i = 1; i <= olis.length; i++) {
		menu = olis[i - 1];

		if (menu.id == name + cursel) {
			menu.className = "cur";

		} else {
			menu.className = "";
		}
	}
	for (j = 0; j < subMenus.length; j++) {
		if (subMenus[j].id.startWith(wMenu)) {
			subMenus[j].style.display = '';
			if (defaultHref == path('/index.html')) {
				defaultHref = subMenus[j].getElementsByTagName('a')[0]
						.getAttribute('href');
				defaultInnerText = subMenus[j].getElementsByTagName('a')[0].innerText;
			}

		} else {
			subMenus[j].style.display = 'none';
		}
	}

	var div = document.getElementById("subNav");
	// var lmenu = document.getElementById("lmenu")
	// div.style.width = westWidth;
	div.style.height = westHeight;
	// div.style.overflow = "auto";
	div.style.overflow = "scroll";
	
	document.title=defaultInnerText;
	window.frames['maincontent'].location.href = defaultHref;
}

// hidden dl --by Neal 2-28
function clickDt(id) {
	var subMenus = Ext.get(id).dom.getElementsByTagName('dd');
	if (subMenus[0].style.display == 'none') {
		for (i = 0; i < subMenus.length; i++) {
			subMenus[i].style.display = '';
		}
	} else {
		for (i = 0; i < subMenus.length; i++) {
			subMenus[i].style.display = 'none';
		}
	}
	// 以下只适用于非IE内核浏览器
	// if (subMenus[0].hidden == false) {
	// for (i = 0; i < subMenus.length; i++) {
	// subMenus[i].hidden = true;
	// }
	// } else {
	// for (i = 0; i < subMenus.length; i++) {
	// subMenus[i].hidden = false;
	// }
	// }
}

//setTitle --by Neal 8-13
function setNewTitle(innerText) {
//	var myahref=document.getElementByTagName("a");
	
	document.title=innerText;
}

/*--------------------function-------------------*/
// input clearText
function clearTxt(obj) {
	var selVal = obj.defaultValue;
	obj.onfocus = function() {
		if (this.value == selVal) {
			this.value = "";
			this.className = this.className.replace('ca7a7', 'c0');
		}
	}
	obj.onblur = function() {
		if (this.value == "") {
			this.value = selVal;
			this.className = this.className.replace('c0', 'ca7a7');
		}
	}
}

// according Menu
function accMenu(lmenu) {
	var lmenuDl = lmenu.getElementsByTagName('dl');
	art.each(lmenuDl, function() {
		var thisDt = this.getElementsByTagName('dt')[0];
		var thisDds = this.getElementsByTagName('dd');
		var flag = true;
		thisDt.onclick = function() {
			art.each(thisDds, function() {
				this.style.display = flag ? "none" : "block";
			})
			flag = !flag;
		}// end_click
	});
}

function showMsgIn() {
	Ext.get('msgAll').hide();
	Ext.get('msgIn').show();
	return false;
}

function showMsgAll() {
	Ext.get('msgAll').show();
	Ext.get('msgIn').hide();
	return false;
}
// 导航条高亮
function highlightPage(mainNav, subNav) {
	var mainNav = mainNav || {};
	var nav = subNav;
	var mainLinks = mainNav.getElementsByTagName('li');
	var links = nav.getElementsByTagName('a');
	var subDivs = subNav.getElementsByTagName('dl');
	for ( var i = 0; i < links.length; i++) {
		var linkurl = links[i].getAttribute('href');
		var currenturl = window.location.href;
		var mainLinksId = (links[i].parentNode.id).split('_')[2] - 1;
		links[i].parentNode.style.display = "none";
		if (currenturl.indexOf(linkurl) != -1) {
			mainLinks[mainLinksId].className = 'cur';
			links[i].parentNode.style.display = "block";
		} else {
			mainLinks[mainLinksId].className = ""
		}
	}
}
// JS codes goes here!
String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
}

String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
}

// neal 监听按键
function doKey(e) {
	var ev = e || window.event;// 获取event对象
	var obj = ev.srcElement || ev.target;// 获取事件源
	var t = obj.tagName || obj.type || obj.getAttribute('type');// 获取事件源类型
	t = t.toUpperCase();
	if (ev.keyCode == 8 && t != "PASSWORD" && t != "TEXT" && t != "TEXTAREA"
			&& t != "INPUT") {
		// ev.returnValue = false;
		return false;
		// ev.cancelBubble = bskEventCancel;
	} else {
		return !obj.readOnly;
	}
	// window.document.onkeydown = doKey(e);
}

// 禁止后退键 作用于Firefox、Opera
document.onkeypress = doKey;
// window.onkeypress=doKey;
// 禁止后退键 作用于IE、Chrome
document.onkeydown = doKey;
// window.onkeypress=doKey;

// neal jsMap
//function Map() {
//	
//	this.arr = new Array();
//	this.get = get;
//	this.put = put;
//	this.remove = remove;
//	this.size = size;
//	this.isEmpty = isEmpty;
//	
//	var struct = function(key, value) {
//		this.key = key;
//		this.value = value;
//	}
//
//	var put = function(key, value) {
//		for ( var i = 0; i < this.arr.length; i++) {
//			if (this.arr[i].key === key) {
//				this.arr[i].value = value;
//				return;
//			}
//		}
//		this.arr[this.arr.length] = new struct(key, value);
//	}
//
//	var get = function(key) {
//		for ( var i = 0; i < this.arr.length; i++) {
//			if (this.arr[i].key === key) {
//				return this.arr[i].value;
//			}
//		}
//		return null;
//	}
//
//	var remove = function(key) {
//		var v;
//		for ( var i = 0; i < this.arr.length; i++) {
//			v = this.arr.pop();
//			if (v.key === key) {
//				continue;
//			}
//			this.arr.unshift(v);
//		}
//	}
//
//	var size = function() {
//		return this.arr.length;
//	}
//
//	var isEmpty = function() {
//		return this.arr.length <= 0;
//	}
//	
//}
function Map()
{
   this.elements = new Array();

   /**
   * 获取MAP元素个数
   */
this.size = function() {
   return this.elements.length;
}

/**
   * 判断MAP是否为空
   */
this.isEmpty = function() {
   return (this.elements.length < 1);
}

   /**
   * 删除MAP所有元素
   */
this.clear = function() {
   this.elements = new Array();
}

   /**
   * 向MAP中增加元素（key, value) 
   * @param {Object} _key 
   * @param {Object} _value
   */
this.put = function(_key, _value) {
   this.elements.push({key:_key, value:_value});
}

   /**
   * 删除指定KEY的元素，成功返回True，失败返回False
   * @param {Object} _key
   */
this.remove = function(_key) {
   var bln = false;
   try {
    for (i = 0; i < this.elements.length; i++) { 
     if (this.elements[i].key == _key){
      this.elements.splice(i, 1);
      return true;
     }
    }
   }catch(e){
    bln = false;    
   }
   return bln;
}

/**
* 获取指定KEY的元素值VALUE，失败返回NULL
* @param {Object} _key
*/
this.get = function(_key) {
   try{   
    for (i = 0; i < this.elements.length; i++) {
     if (this.elements[i].key == _key) {
      return this.elements[i].value;
     }
    }
   }catch(e) {
    return null;   
   }
}

/**
* 获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
* @param {Object} _index
*/
this.element = function(_index) {
   if (_index < 0 || _index >= this.elements.length)
   {
    return null;
   }
   return this.elements[_index];
}

/**
* 判断MAP中是否含有指定KEY的元素
* @param {Object} _key
*/
this.containsKey = function(_key) {
   var bln = false;
   try {
    for (i = 0; i < this.elements.length; i++) { 
     if (this.elements[i].key == _key){
      bln = true;
     }
    }
   }catch(e) {
    bln = false;    
   }
   return bln;
}
    
/**
* 判断MAP中是否含有指定VALUE的元素
* @param {Object} _value
*/
this.containsValue = function(_value) {
   var bln = false;
   try {
    for (i = 0; i < this.elements.length; i++) { 
     if (this.elements[i].value == _value){
      bln = true;
     }
    }
   }catch(e) {
    bln = false;    
   }
   return bln;
}

/**
* 获取MAP中所有VALUE的数组（ARRAY）
*/
this.values = function() {
   var arr = new Array();
   for (i = 0; i < this.elements.length; i++) { 
    arr.push(this.elements[i].value);
   }
   return arr;
}

/**
* 获取MAP中所有KEY的数组（ARRAY）
*/
this.keys = function() {
   var arr = new Array();
   for (i = 0; i < this.elements.length; i++) { 
    arr.push(this.elements[i].key);
   }
   return arr;
}
}


function formatnumber(s){
	var ss = "";
	ss = s;
	ss = ss.replace(/[^(\d|\.)]/g,'');
	if(ss==''){
		s.value='0.00';
	}else{
//		s.value=parseFloat(ss).toLocaleString().replace(/,/g, "");
		s.value=new Number(ss). toFixed([fractionDigits]);
	}
}

/*导航栏*/
function GlobalNav(){
	this.globalNavLinks=Ext.select('#navWrap li ul a');
	this.masterNav=Ext.select('#navWrap .master_nav');
	this.setDefaultLink();
	this.mouseTab();
}

GlobalNav.prototype={
	setDefaultLink:function (){
		var me=this;
		me.globalNavLinks.each(function(){
			if(location.href.indexOf(this.dom.href) != -1){
                if (this.parent()) {
                    me.setCurCls(this.parent().parent().parent());
                    me.setCurInCls(this.parent());
                }
			}
		});
	},
    setCurInCls:function(objCmp){
        this.globalNavLinks.removeCls('curIn');
		objCmp.addCls('curIn');
    },
	mouseTab:function(){
		var me=this;
		me.masterNav.on('mouseover',function(){
			me.setCurCls(Ext.get(this));
		});
		//Ext.select('#navWrap').on('mouseleave',function(){
		//	me.setDefaultLink();
		//});
	},
	setCurCls:function(objCmp){
		this.masterNav.removeCls('cur');
		objCmp.addCls('cur');
	}
}
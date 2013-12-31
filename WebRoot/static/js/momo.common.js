/* author:cray@immomo.com */
$(document).ready(function(){
	//momoResize();
	//document.body.onresize = momoResizeDrag;
	//if(jQuery.browser.msie && (jQuery.browser.version == "6.0"))
		//correctPNG(); 
});

function momoResizeDrag(){
	$().mouseup(momoResize);
}

function momoResize(){
	var bodyheight = PosU.getHeight($(document));//window.screen.availHeight;//window.screen.availHeight;
	var footer = $("#Footer");
	var footerspring = $("#FooterSpring");
	//alert("offset:" + PosU.offsetY(footer) + ", height:" + PosU.getHeight(footer) + ",body:" + bodyheight);
	if(PosU.offsetY(footer) + PosU.getHeight(footer) < bodyheight){
		footerspring.css({"height": bodyheight - PosU.offsetY(footer) - PosU.getHeight(footer) +"px"});
	}
}

//全局统一的定位获取对象 by Cray.Lin
var PosU = {
    // 设置弹出层跟随对象显示并计算相对位置
    getOffsetX: function(obj, positionNum)
    {
        var $this = this;
        var jqObj = jQuery(obj);
        obj = jqObj[0];
        var x = $this.offsetX(obj);
        //        if(frameElement)
        //            x += $this.offsetX(frameElement);
        return x + positionNum; //POPUCONTROL.iframeX;
    },
    // 设置弹出层跟随对象显示并计算相对位置
    getOffsetY: function(obj, positionNum)
    {
        var $this = this;
        var $obj = jQuery(obj);
        obj = $obj[0];
        var y = $this.offsetY(obj);
        //y += $this.getHeight($obj);
        //        if(frameElement)
        //            y += $this.offsetY(frameElement);
        return y + positionNum; //POPUCONTROL.iframeY;
    },
    //获得卷屏高度
    getScrollTop: function()
    {
        var scrollTop = 0;
        if (document.documentElement && document.documentElement.scrollTop)
            scrollTop = document.documentElement.scrollTop;
        else if (document.body)
            scrollTop = document.body.scrollTop;
        return scrollTop;
    },
    //取得页面在当前屏幕内可视区域的宽度
    getInnerW: function()
    {
        return (window.innerWidth) ? window.innerWidth : (document.documentElement && document.documentElement.clientWidth) ? document.documentElement.clientWidth : document.body.offsetWidth;
    },
    //取得页面在当前屏幕内可视区域的高度
    getInnerH: function()
    {
        return (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.offsetHeight;
    },
    //取得对象距离顶部的值
    offsetY: function(obj)
    {
        if (typeof(obj) == "undefined")
            return 0;
        var jqObj = jQuery(obj);
        obj = jqObj[0];
        if (typeof(obj) == "undefined")
            return 0;
        var y = 0;
        if ( typeof(obj.offsetTop) != "undefined")
            y += obj.offsetTop;
        while (obj = obj.offsetParent)
            if (obj.offsetTop)
            y += obj.offsetTop;
        return y;
    },
    //取得对象距离顶部的值
    offsetX: function(obj)
    {
        if (typeof(obj) == "undefined")
            return 0;
        var jqObj = jQuery(obj);
        obj = jqObj[0];
        if (typeof(obj) == "undefined")
            return 0;
        var x = 0;
        if (typeof(obj.offsetLeft) != "undefined")
            x += obj.offsetLeft;
        while (obj = obj.offsetParent)
            if (obj.offsetLeft)
            x += obj.offsetLeft;
        return x;
    },
    //取得对象高度值
    getHeight: function(obj)
    {
        var $obj = jQuery(obj);
        var h = $obj.height();
        try{
            var tmpH = 0;
            tmpH = parseInt($obj.css("padding-top").replace("px", ""));
            if (tmpH != NaN && tmpH > 0) h += tmpH;
            tmpH = parseInt($obj.css("padding-bottom").replace("px", ""));
            if (tmpH != NaN && tmpH > 0) h += tmpH;
            tmpH = parseInt($obj.css("border-width").replace("px", ""));
            if (tmpH != NaN && tmpH > 0) h += tmpH * 2;
        } catch(e) {
            return h;
        }
        return h;
    }
};

function getUrlPara(paraName){  
    var sUrl  =  location.href; 
    var sReg  =  "(?:\\?|&){1}"+paraName+"=([^&]*)" 
    var re=new RegExp(sReg,"gi"); 
    re.exec(sUrl); 
    return RegExp.$1;
} 

function correctPNG()
   {
   for(var i=0; i<document.images.length; i++)
      {
     var img = document.images[i]
     var imgName = img.src.toUpperCase()
     if (imgName.substring(imgName.length-3, imgName.length) == "PNG")
        {
       var imgID = (img.id) ? "id='" + img.id + "' " : ""
       var imgClass = (img.className) ? "class='" + img.className + "' " : ""
       var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
       var imgStyle = "display:inline-block;" + img.style.cssText
       if (img.align == "left") imgStyle = "float:left;" + imgStyle
       if (img.align == "right") imgStyle = "float:right;" + imgStyle
       if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle     
       var strNewHTML = "<span " + imgID + imgClass + imgTitle
       + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
        + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
       + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>"
       img.outerHTML = strNewHTML
       i = i-1
        }
      }
   }
function alphaBackgrounds(){
   var rslt = navigator.appVersion.match(/MSIE (d+.d+)/, '');
   var itsAllGood = (rslt != null && Number(rslt[1]) >= 5.5);
   for (i=0; i<document.all.length; i++){
      var bg = document.all[i].currentStyle.backgroundImage;
      if (bg){
         if (bg.match(/.png/i) != null){
            var mypng = bg.substring(5,bg.length-2);
    //alert(mypng);
            document.all[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+mypng+"', sizingMethod='crop')";
            document.all[i].style.backgroundImage = "url('')";
    //alert(document.all[i].style.filter);
         }                                               
      }
   }
}

function setMainMenu(v){
    var m = $("#MainMenu").find('.lk');//.children();
    m.eq(v).addClass("on").siblings().removeClass("on");
}
function setSubMenu(v){
    var m = $("#SubMenu").children();
    m.eq(v).addClass("on").siblings().removeClass("on");
}
function setFooterMenu(v){
var m = $("#FooterMenu").children();
m.eq(v).addClass("on").siblings().removeClass("on");
}
function CountRoll(){
    var v = $("#UserCount .Cover");
    var b = $("#UserCount .Bg1");
    var vt = parseInt(v.css("top"));
    var vh = parseInt(v.css("height"));
    var bt = parseInt(b.css("top"));
    var bh = parseInt(b.css("height"));
    var f = $("#UserCountNumber").css("color","#f4f5ef");
    b.animate({top:bt+bh+"px",height:bh-bh+"px"},50,
        function(){
            v.delay(100).animate({height:vh+20+"px"},50,
                function(){ f.text(parseInt(f.text())+1); });
            v.animate({top:vt+"px",height:vh+"px"},50,
                function(){
                    b.css({top:bt+"px",height:bh+"px"});
                    f.css("color","#fff");
                });
        }
    )
    //b.animate({top:bt+"px",height:bh+"px"},200,function(){ $("#msg").html(bt) }).delay(100);
    
}

var autoNav = function() {
    this.obj = null;
    this.timespan = 6000;
    this.btn = { };
    this.timer = null;
    this.current = 0;
    this.count = 0;
    this.direction = "x";
    this.singlewidth = 0;
};
autoNav.prototype.init = function(h){
    var $this = this;
    h = h || {};
    if (typeof(h.obj) != "undefined") this.obj = $(h.obj); else return;
    if (typeof(h.timespan) != "undefined") this.timespan = h.timespan;
    if (typeof(h.btn) != "undefined") this.btn = h.btn;
    if (typeof(h.direction) != "undefined") this.direction = h.direction;
    this.count = this.obj.children().length;
    if(this.direction == "x")
        this.singlewidth = this.obj.children().eq(0).width();
    else 
        this.singlewidth = this.obj.children().eq(0).height();
    this.btn.eq(this.current).addClass("on");
    this.obj.css({'position':'absolute'}).parent().css({'position':'relative'});
    $(this.obj).hover(function(){$this.stop()},function(){$this.start()});
    $(this.btn).hover(function(){$this.stop()},function(){$this.start()});
    $.each($(this.btn), function(i,o){
        $(o).click(function(){$this.goto(i)});
    });
    this.start();
}
autoNav.prototype.play = function(){
    this.current+=1;
    if(this.current==this.count)
        this.current=0;
    if(this.direction == "x"){
        this.obj.animate({left:-this.singlewidth*this.current+'px'});
    }else{
        this.obj.animate({top:-this.singlewidth*this.current+'px'});
    }
    this.btn.eq(this.current).addClass("on").siblings().removeClass("on");
};
autoNav.prototype.start = function(){
    $this = this;
    this.timer = setInterval(function(){$this.play()},$this.timespan);
};
autoNav.prototype.stop = function(){
    clearInterval(this.timer);
};
autoNav.prototype.stopat = function(i){
    clearInterval(this.timer);
    this.goto(i);
};
autoNav.prototype.goto = function(i){
    if(i>this.count) return;
    this.current=i;
    if(this.direction == "x"){
        this.obj.animate({left:-this.singlewidth*this.current+'px'});
    }else{
        this.obj.animate({top:-this.singlewidth*this.current+'px'});
    }
    this.btn.eq(this.current).addClass("on").siblings().removeClass("on");
};

/* author:cray@immomo.com */
(function($){ 
    $.fn.wDrag=function(h){return i(this,h);}; 
    $._wDrag={dnr:{},e:0, 
        drag:function(v){
            if(M.h.d=="xy")
                E.css({left:M.X+v.pageX-M.pX,top:M.Y+v.pageY-M.pY});
            else if(M.h.d=="x")
                E.css({left:M.X+v.pageX-M.pX});
            else if(M.h.d=="y")
                E.css({top:M.Y+v.pageY-M.pY});
            return false;
        }, 
        stop:function(){ 
            E.css('opacity',M.o);
            document.onmousemove = null;
            document.onmouseup = null;
            //$(document).unbind('mousemove',J.drag).unbind('mouseup',J.stop);
            //水平方向
            if(M.h.d.indexOf('x') >= 0){
                var P=parseInt(M.h.obj.attr('lastpos'));
                //$("#msg").text("last:"+P+",this:"+f('left'));
                if((P==0 && f('left')-P>M.h.o)
                    || (P==-(M.h.obj.width()-M.h.s) && P-f('left')>M.h.o)
                    ){
                    //第一屏向左或最后一屏向右 还原位置
                    E.animate({left:P+'px'});
                }
                else if(f('left')-P>M.h.o){
                    //移动到左一屏
                    P+=M.h.s;
                    M.h.obj.attr('lastpos',P);
                    E.animate({left:P+'px'});
                }
                else if(P-f('left')>M.h.o){
                    //移动到右一屏
                    P-=M.h.s;
                    M.h.obj.attr('lastpos',P);
                    E.animate({left:P+'px'});
                }
                else
                {
                    //不足以移动，还原
                    E.animate({left:P+'px'});
                }
            }
            //垂直方向
            else{
                var P=parseInt(M.h.obj.attr('lastpos'));
                //$("#msg").text("last:"+P+",this:"+f('top'));
                if((P==0 && f('top')-P>M.h.o)
                    || (P==-(M.h.obj.height()-M.h.s) && P-f('top')>M.h.o)
                    ){
                    E.animate({top:P+'px'});
                }
                else if(f('top')-P>M.h.o){
                    P+=M.h.s;
                    M.h.obj.attr('lastpos',P);
                    E.animate({top:P+'px'});
                }
                else if(P-f('top')>M.h.o){
                    P-=M.h.s;
                    M.h.obj.attr('lastpos',P);
                    E.animate({top:P+'px'});
                }
                else
                {
                    E.animate({top:P+'px'});
                }
            }
            //处理可见性
            var I=Math.abs(P/M.h.s);
            E.children().eq(I).siblings().children().hide();
            E.children().eq(I).children().show();
            E.children().eq(I).prev().children().show();
            E.children().eq(I).next().children().show();
        }
    };
    var J=$._wDrag,M=J.dnr,E=J.e, 
    i=function(e,h){
        h = h || {}
        h.obj = $(e);//父物件
        h.obj.attr('lastpos','0');
        h.obj.css({'position':'relative'});
        if (undefined == h.d) h.d = "xy";  //拖拽方向 xy:随意 x:水平 y:垂直
        if (undefined == h.o) h.o = 20;  //拖拽触发动作距离（像素）
        if (undefined == h.s) h.s = 100;  //拖拽单元格距离（像素）
        
        return $.each($(e).children(), function(i,o){
            o.onmousedown = function (v) {
                var d=o,p={},_h=h;E=h.obj;
                if(E.css('position') != 'relative'){try{ E.position(p);}catch(e){}}
                M={X:p.left||f('left')||0,Y:p.top||f('top')||0,W:f('width')||E[0].scrollWidth||0,H:f('height')||E[0].scrollHeight||0,pX:v.pageX,pY:v.pageY,h:_h,o:E.css('opacity')};
                E.css({opacity:1});
                //$().bind("mousemove",$._wDrag.drag);
                //$().bind("mouseup",$._wDrag.stop);
                document.onmousemove = $._wDrag.drag;
                document.onmouseup = $._wDrag.stop;
                return false;
            }
        });
    }, 
    f=function(k){return parseInt(E.css(k))||false;};
})(jQuery);


var hexcase = 0;  
var b64pad  = ""; 
var chrsz   = 8;  
function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function calcMD5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function md5_vm_test(){return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";}
function core_md5(x, len){
  x[len >> 5] |= 0x80 << ((len) % 32);
  x[(((len + 64) >>> 9) << 4) + 14] = len;
  var a =  1732584193;
  var b = -271733879;
  var c = -1732584194;
  var d =  271733878;
  for(var i = 0; i < x.length; i += 16)
  {
    var olda = a;
    var oldb = b;
    var oldc = c;
    var oldd = d;

    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);
    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);
    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);
    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

    a = safe_add(a, olda);
    b = safe_add(b, oldb);
    c = safe_add(c, oldc);
    d = safe_add(d, oldd);
  }
  return Array(a, b, c, d);
}
function md5_cmn(q, a, b, x, s, t){
  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
}
function md5_ff(a, b, c, d, x, s, t)
{
  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t)
{
  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t)
{
  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t)
{
  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

function core_hmac_md5(key, data)
{
  var bkey = str2binl(key);
  if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

  var ipad = Array(16), opad = Array(16);
  for(var i = 0; i < 16; i++) 
  {
    ipad[i] = bkey[i] ^ 0x36363636;
    opad[i] = bkey[i] ^ 0x5C5C5C5C;
  }

  var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
  return core_md5(opad.concat(hash), 512 + 128);
}
function safe_add(x, y){
  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
  return (msw << 16) | (lsw & 0xFFFF);
}
function bit_rol(num, cnt){
  return (num << cnt) | (num >>> (32 - cnt));
}
function str2binl(str){
  var bin = Array();
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < str.length * chrsz; i += chrsz)
    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
  return bin;
}
function binl2hex(binarray){
  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i++)
  {
    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
           hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
  }
  return str;
}
function binl2b64(binarray){
  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i += 3)
  {
    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
                | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
                |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
    for(var j = 0; j < 4; j++)
    {
      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
    }
  }
  return str;
}

function setcookie(cookieName, cookieValue, seconds, path, domain, secure) {
    var expires = new Date();
    expires.setTime(expires.getTime() + seconds * 1000);
    domain = !domain ? "" : domain;
    path = !path ? "" : path;
    document.cookie = escape(cookieName) + '=' + escape(cookieValue)
        + (expires ? '; expires=' + expires.toGMTString() : '')
        + (path ? '; path=' + path : '/')
        + (domain ? '; domain=' + domain : '')
        + (secure ? '; secure' : '');
}
function getcookie(name) {
    var cookie_start = document.cookie.indexOf(name);
    var cookie_end = document.cookie.indexOf(";", cookie_start);
    return cookie_start == -1 ? '' : document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length));
}

$.fn.mRadio = function(options) {
	var _cfg = {
		index : 0,
		radios : [],// the tab buttons.
		input:'',
		indexChanged:function(index,_cfg){}
	};
	if (options)
		$.extend(_cfg, options);
	
	var This=this;
	var radios=This.find('label');
	_cfg.radios=radios;
	var index;
	radios.each(function (){
		$(this).unbind('click');
		$(this).click(function(){			
			var o=$(this);
			if(o.hasClass('disabled')) return;
			radios.each(function(){$(this).removeClass('on');})
			index=radios.index(o);
			o.addClass('on');
			var val=o.attr('_value');
			This.find(_cfg.input).val(val);
			_cfg.indexChanged(index,_cfg);
		});
		if($(this).hasClass('on')) $(this).click();
	});
};
var InputNotice=function(obj){
	var _value=$(obj).attr('_notice');
	var checkEmpty=function(){
		var inputval=$(obj).val();
		if(inputval=='') {
			$(obj).val(_value);
			$(obj).css({color:"#aaa"});
		}
	};
	checkEmpty();
	$(obj).bind('focus',function(){
		var inputval=$(obj).val();
		if(inputval==_value) {
			$(obj).val('');
		}
		$(obj).css({color:"#000"});
	}).bind('blur',function(){
		checkEmpty();
	});
};
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		var value=this.value || '';
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}					
			o[this.name].push(value);
		} else {
			o[this.name] =value;
		}
	});
	return o;
};
$.fn.scrollImg = function(){
	$(this).each(function(){
		var $this = $(this),
			scrollMain = $this.find('.scroll-main'),
			elmUl = scrollMain.find('ul'),
			elmLi = elmUl.find('li'),
			nLen = elmLi.length,
			scrollBtn = $this.find('.scroll-btn').length?$this.find('.scroll-btn a'):'',
			next = $this.find('.next'),
			prev = $this.find('.prev'),
			oneWidth = elmLi.eq(0).outerWidth(),
			timer = null,index = 0;
		elmUl.css('width',oneWidth*nLen);
		var doMove = function(ind){
			var ind = ind;
			if(!!scrollBtn){
				scrollBtn.removeClass('on').eq(ind).addClass('on');
			}
			elmUl.stop().animate({'left':-ind*oneWidth});
		}
		function autoPlay(){
			timer = setInterval(function(){
				index++;
				index>nLen-1&&(index=0);
				doMove(index);
			},3000);
		}
		if(!!scrollBtn){
			scrollBtn.bind('click',function(){
				if($(this).hasClass('on')){return !1;}
				index = $(this).index();
				doMove(index);
			})
		}
		autoPlay();
		$this.on({
			mouseover:function(){clearInterval(timer)},
			mouseout:function(){autoPlay()}
		});
		if(next.length){
			next.on('click',function(){
				index++;
				index>nLen-1&&(index=0);
				doMove(index);
			})
		}
		if(prev.length){
			prev.on('click',function(){
				index--;
				index<0&&(index=nLen-1);
				doMove(index);
			})
		}
	});
};
var momo={};
momo.admin={};
momo.admin.common={};
momo.admin.common.districtSelectorHandler = {
	    data_src:{
	    		country:"/static/admin/LocalList-ww_cn.xml",
	    		province:"/static/admin/LocalList-ww_cn.xml",
	    		city:"/static/admin/LocalList-zh_cn.xml",
	    		district: "/static/admin/LocalList-zh_cn.xml"
	    },
	    ctrl_ids:{
	    	country:"#event_country",
			province:"#event_province",
			city:"#event_city",
			district: "#event_district"
	    },
	    ctrl_prefix:'#event_',
	    init: function() {
	        if (!window.initDistrict) {
	            window.initDistrict = {
	                country: '中国',
	                province: null,
	                city: null,
	                district:null
	            };
	        }
	        districtSelectorHandler.loadList('country', true);
	        districtSelectorHandler.loadList('province', true, window.initDistrict.country);
	        districtSelectorHandler.loadList('city', true, window.initDistrict.country, window.initDistrict.province);
	        districtSelectorHandler.loadList('district', true, window.initDistrict.country, window.initDistrict.province, window.initDistrict.city);
	        districtSelectorHandler.bind();
	    },
	    bind: function() {
	        $(districtSelectorHandler.ctrl_ids.country).change(function() {
	            districtSelectorHandler.loadList('province', false, $(districtSelectorHandler.ctrl_ids.country).val());
	            districtSelectorHandler.unloadList('city');
	        });
	        $(districtSelectorHandler.ctrl_ids.province).change(function() {
	            districtSelectorHandler.loadList('city', false, $(districtSelectorHandler.ctrl_ids.country).val(), $(districtSelectorHandler.ctrl_ids.province).val());
	            districtSelectorHandler.unloadList('district');	            
	        });
	        $("#event_city").change(function(){
	            districtSelectorHandler.loadList('district', false,$(districtSelectorHandler.ctrl_ids.country).val(), $(districtSelectorHandler.ctrl_ids.province).val(),$("#event_city").val());
	        });
	    },
	    callback_province:null,
	    callback_city:null,
	    loadList: function(type, selectByInit, country, province ,city) {
	        districtSelectorHandler.unloadList(type);
	        var node = "";
	        if (type == 'country') {
	            node = 'CountryRegion';
	        } else if (type == 'province') {
	            node = 'CountryRegion[Code="' + country + '"] State';
	        } else if (type == 'city') {
	            node = 'CountryRegion[Name="' + province + '"] State';
	        } else if(type == 'district'){
	        	node = 'State[Name="' + city + '"] City';
	        }
	        $.get(districtSelectorHandler.data_src[type], function(xml) {
	            var data = $(xml).find(node);
	            for (var i = 0; i < data.length; i++) {
	                var code = $(data[i]).get(0).getAttribute('Code');
	                var name = $(data[i]).get(0).getAttribute('Name');
	                $(districtSelectorHandler.ctrl_prefix + type).append('<option value="' + name + '">' + name + '</option>');
	                if (selectByInit) {
	                    if (type == 'country') {
	                        districtSelectorHandler.select(type, window.initDistrict.country);
	                    } else if (type == 'province') {
	                        districtSelectorHandler.select(type, window.initDistrict.province);
	                    } else if (type == 'city') {
	                        districtSelectorHandler.select(type, window.initDistrict.city);
	                    } else if(type == 'district') {
	                    	districtSelectorHandler.select(type, window.initDistrict.district);
	                    }
	                }
	                if(type == 'province'){
	                    if('function'==typeof (districtSelectorHandler.callback_province)) districtSelectorHandler.callback_province();
	                }else if(type == 'city') {
	                    if('function'==typeof (districtSelectorHandler.callback_city)) districtSelectorHandler.callback_city();
	                }
	            }
	        });
	    },
	    unloadList: function(type) {
	        $(districtSelectorHandler.ctrl_prefix + type + ' option[value!=""]').remove();
	    },
	    select: function(type, value) {
	        $(districtSelectorHandler.ctrl_prefix + type).val(value);
	    }
	};
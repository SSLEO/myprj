//변수
var UserAgent = navigator.userAgent;

// 널체크
function checkNullStr(s){
	if (s.length+1 == s.split(" ").length) {		
		return false;
    	}
    	return true;
}


// 숫자인지 체크
function IsNum(numstr){
	var tempstr = "0123456789";
	for(var i=0;i<numstr.length;i++) 
	{
		if(tempstr.indexOf(numstr.charAt(i)) == -1) {
			return false;
		}
	}
	return true;
}
// 2014.12.29, 숫자인지 체크
function IsNum2(numstr){
	var pattern = /^[0-9]+$/;
	for(var i=0;i<numstr.length;i++) 
	{
		if(pattern.test(numstr.charAt(i))) {
			return false;
		}
	}
	return true;
}


//숫자인지 체크
function checkNum (object)
{
	with (object)
	{
		checkValue=object.value;
		realValue=0;
		if (checkValue == "")
			return true;
		if (!checkValue.match (/^[\d]+$/))
		{
				object.value=""
				object.focus();
				alert("숫자만 입력해 주세요.");
				return false;
		}else if ( checkValue == "0"){
		
				object.value=""
				object.focus();
				alert("'0' 이상만 가능 합니다.");
				return false;
		
		}
		return true;
	}
}

// 숫자,특수문자 불가 체크
function IsChar(aStr, aLen, aHan){
        var isValid = true;
        for (var i=0; i< aLen; i++){
          if (aHan == "ENG") {
             if (!( (aStr.charCodeAt(i) >= 65 && aStr.charCodeAt(i) <= 90 ) ||
                    (aStr.charCodeAt(i) >= 97 && aStr.charCodeAt(i) <= 122) )) {
                isValid = false;
                break;
             }
          } else if (aHan == "HAN") {
             if (!( aStr.charCodeAt(i) >  127 )  )  {
                isValid = false;
                break;
             }
          } else {
             if (!( (aStr.charCodeAt(i) >= 65 && aStr.charCodeAt(i) <= 90 ) ||
                    (aStr.charCodeAt(i) >= 97 && aStr.charCodeAt(i) <= 122) ||
                    (aStr.charCodeAt(i) >  127 ) ))  {
                isValid = false;
                break;
             }
          } 
        } 
        return isValid;
}




// 주민번호 유효성체크
function IsResId(id1, id2){
	a = Array(6);
	b = Array(7);

	for (var i=0; i<6; i++)
		a[i] = parseInt(id1.charAt(i));

	for (var j=0; j<7; j++)
		b[j] = parseInt(id2.charAt(j));

	ssntot = (a[0]*2)+(a[1]*3)+(a[2]*4)+(a[3]*5)+(a[4]*6)+(a[5]*7) + (b[0]*8)+(b[1]*9)+(b[2]*2)+(b[3]*3)+(b[4]*4)+(b[5]*5);
	ssnave = 11 - (ssntot%11);

	if (ssnave == 11) ssnave = 1;
	else if(ssnave == 10) ssnave = 0;

	if (b[6] == ssnave) return true;
	else return false;
}

// 이메일주소 유효성체크
function IsMailStr(aStr){
     tempstr = "0123456789abcdefghijklmnopqrstuvwxyz_-@.";
     str1cnt = 0;
     str2cnt = 0;
     for(i=0;i<aStr.length;i++) { 
       if(tempstr.indexOf(aStr.charAt(i)) == -1) return false;
       if(aStr.charAt(i) == '@')  str1cnt += 1;
       if(aStr.charAt(i) == '.')  str2cnt += 1;
     } 
     if (str1cnt != 1 || str2cnt < 1 || str2cnt > 2) return false;
     return true;
}

// 전화 지역번호 체크
function IsValidDDD(obj){
        if ( obj == '02'  || obj == '031' || obj == '032' || obj == '033' ||
             obj == '041' || obj == '042' || obj == '043' || obj == '051' ||
             obj == '052' || obj == '053' || obj == '054' || obj == '055' ||
             obj == '061' || obj == '062' || obj == '063' || obj == '064' ||
             obj == '011' || obj == '017' || obj == '016' || obj == '018' ||
             obj == '019'
           )
            return true;
        else
            return false;
}

// 전화번호 4자리로 채워줌(스페이스로 채움)("012"-->"012 ")
function GetFourDigit(v){
	var ret="";
	if(v.length==4){ ret=v; }
	else if(v.length==3){ ret=v+" "; }
	else if(v.length==2){ ret=v+"  "; }
	else{ ret=v+"   "; }
	return ret;
}

// 날짜형식체크
function IsDate(src){
	if(src.length==8){
		if(src.substring(4,6)<13){
			if(src.substring(6,8)>0 && src.substring(6,8)<32){
				return true;
			}
		}
	}
	return false;
}

// 공백 필드 체크 후 alert 메세지, 포커싱 처리
function feildCheck(field, msg, bForcus){
	if ( !checkNullStr(field.value)) {
		alert(msg);

		if(bForcus != null && bForcus){
			field.focus();
		}
	
		return false;
	}else{
		return true;
	}
}


//str은 모두 소문자여야하고 첫글자는 영문이어야 한다. 영문과 0~9, _ 는 허용한다. 
function CheckChar(str) { 
    strarr = new Array(str.length); 
    var flag = true; 
    for (i=0; i<str.length; i++) { 
        strarr[i] = str.charAt(i) 
        if (i==0) { 
            if (!((strarr[i]>='a')&&(strarr[i]<='z'))) { 
                flag = false; 
            } 
        } else { 
            if (!((strarr[i]>='a')&&(strarr[i]<='z')||(strarr[i]>='0')&&(strarr[i]<='9')||(!IsChar(strarr[i])))) { 
                flag = false; 
            } 
        } 
    } 
    if (flag) { 
        return true; 
    } else { 
        return false; 
    } 
} 

//str은  영어여야하고  영문과 0~9, _ 는 허용한다. 
function CheckChar1(str) { 
  strarr = new Array(str.length); 
  var flag = true; 
  for (i=0; i<str.length; i++) { 
      strarr[i] = str.charAt(i) 
      if (!((strarr[i]>='a')&&(strarr[i]<='z')||(strarr[i]>='A')&&(strarr[i]<='Z')||(strarr[i]>='0')&&(strarr[i]<='9'))) { 
    	  flag = false; 
      }
  }
  if (flag) { 
      return true; 
  } else { 
      return false; 
  } 
}
//str은 모두 영문소문자여야 한다. 
function CheckChar2(str) { 
    strarr = new Array(str.length); 
    var flag = true; 
    for (i=0; i<str.length; i++) { 
        strarr[i] = str.charAt(i) 
        if (!((strarr[i]>='a')&&(strarr[i]<='z'))) { 
            flag = false; 
        } 
    } 
    if (flag) { 
        return true; 
    } else { 
        return false; 
    } 
} 



//str은 한글이어야만 한다. 
function CheckHangul(str) { 
    strarr = new Array(str.length); 
    schar = new Array('/','.','>','<',',','?','}','{',' ','\\','|','(',')','+','='); 
    flag = true; 
    for (i=0; i<str.length; i++) { 
        for (j=0; j<schar.length; j++) { 
            if (schar[j] ==str.charAt(i)) { 
                flag = false; 
            } 
        } 
        strarr[i] = str.charAt(i) 
        if ((strarr[i] >=0) && (strarr[i] <=9)) { 
            flag = false; 
        } else if ((strarr[i] >='a') && (strarr[i] <='z')) { 
            flag = false; 
        } else if ((strarr[i] >='A') && (strarr[i] <='Z')) { 
            flag = false; 
        } else if ((escape(strarr[i]) > '%60') && (escape(strarr[i]) <'%80') ) { 
            flag = false; 
        } 
    } 
    if (flag) { 
        return true; 
    } else { 
        return false; 
    }
}

function isIncludeHangul(str){

	var check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	var chk_han = str.match(check);

	return ( chk_han != null );
}

//이메일 체크
function email_check(value)
{
	var flag = false; //true when validation successful.
	var msg = "";

	if (value == "") return flag; //입력값 없는 경우는 Pass

	var tsTarget = value;
	var regExpEmail = /^\w+((-|\.)\w+)*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]{2,4}$/;

	return regExpEmail.test(tsTarget);
}


/** 숫자 인지 여부 **/
function isNumeric(s)
{
/*
     var isNum = /\d/;
     if( !isNum.test(s) ) return 0; 
     return 1;
*/
	var pattern = /^[0-9]+$/;
	return (pattern.test(s)) ? true : false;
}

/** 문자열의 바이트 길이를 되돌린다 **/ //호인 주석 : 특수 키와 소문자 영문자 영어만 1byte
function getByte(str) {
	
	if ( str == null || str == "" ) {
		return 0;
	}
	
    var len = 0;
    str = this != window ? this : str;
    for (j=0; j<str.length; j++) {
        var chr = str.charAt(j);
        len += (chr.charCodeAt() > 128) ? 2 : 1;
    }
    return len;
}


/**
70. 해당 객체의 입력값 검사 및 byte 수 검사
1. 숫자 / 한글 / 영문
2. byte 수
3. 다음 입력 박스
**/
function checkInputCondition(){

	var i = -1;
	var checkValue = "";
	var args = checkInputCondition.arguments;
	var oTargetObj = args[++i];
	var validType = args[++i];
	var valueByte = args[++i];
	var nextInput = args[++i];	

	checkValue = oTargetObj.value;
	var bResult = true;
	var errMsg = "";
	
	if ( checkValue == "" ){
	}else{
		if ( validType == "number" ){
			bResult = isNumeric(checkValue);
			errMsg = "숫자만 입력하세요.";
		}
		else if ( validType == "han" ){
			bResult = isHanOnly(checkValue);
			errMsg = "한글만 입력하세요.";
		}
		else if ( validType == "eng" ){
			bResult = isEnglishStr(checkValue);
			errMsg = "영문만 입력하세요.";
		}
		else{
		}
	}
	// 검사 결과
	if ( !bResult ){
		// alert(errMsg);
		try{
			oTargetObj.value = "";
			oTargetObj.focus();
		}
		catch(e){
		}
		return false;
	}

	checkValue = oTargetObj.value;
	var currentValueByte = getByte(checkValue);
	// alert(currentValueByte);
	if ( currentValueByte > valueByte ){
		alert("입력된 값의 길이는 " + valueByte + " 바이트 까지 가능합니다.");
		try{
			oTargetObj.focus();
		}
		catch(e){
		}
		return false;
	}
	else if ( currentValueByte == valueByte && nextInput != null ){
		try{
			nextInput.focus();
		}
		catch(e){
		}
	}

	return true;

}


/** 문자열을 바이트 단위로 자른다. **/
function cut_str(str, length)
{
	var args = cut_str.arguments;
	var glueString = "";
	var bGlueString = false;
	if ( args.length == 3 )
	{
		glueString = args[2];
	}

	var tmpStr;
	var temp=0;
	var onechar;
	var tcount;
	var cutStr = "";
	tcount = 0;

	tmpStr = new String(str);
	temp = tmpStr.length;

	if ( getByte(str) > length )
	{
		bGlueString = true;
	}

	for(k=0;k<temp;k++)
	{
		var oneCharByte = getByte(tmpStr.charAt(k));

		if ( ( getByte(cutStr) + oneCharByte ) > length )
		{
		}
		else{
			cutStr += tmpStr.charAt(k);
		}
	}

	if ( bGlueString )
	{
		cutStr += glueString;
	}

	return cutStr;
}

/*
1000 자리 콤마 표시
*/
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/*
support debug //console
*/
function debugconsole(str){
		try{
			//console.log(str);
		}catch(e){
		}
}

/*
 * 날짜 비교
 */
function getDate(date1, date2){
	var startDate = date1.split("-");
	var endDate = date2.split("-");
	
	var sDate = new Date(startDate[0], startDate[1], startDate[2]).valueOf();
	var eDate = new Date(endDate[0], endDate[1], endDate[2]).valueOf();
	
	if(sDate > eDate){
		return true;
	}else if(sDate == eDate){
		return false;
	}else{
		return false;
	}
}

/**
 * 문자열 길이값 반환 한글 포함
 */
function getByteStrLength(str)
{
	var len = 0;
	if(str == null)
	{
		return 0;
	}
	for(var i = 0 ; i < str.length ; i++)
	{
		var c = escape(str.charAt(i));
		if(c.length == 1) 
		{
			len++;
		}
		else if(c.indexOf("%u") > -1)
		{
			len += 2;
		
		}
		else if(c.indexOf("%") > -1)
		{
			len += c.length/3;
		}
		if(c == "%0D")
		{
			len += 1;
		}
		if(c == "%0A")
		{
			len += 1;
		}
	}
	return len;
}


/**
 * @description 완성형 한글 체크
 * @param {String} s 체크할 문자열
 * @return {Boolean}
 * */
function checkCompletedHangul( s ){
	var ustr = escape( s ),
	c,
	result;

	if( ustr.indexOf( '%' ) > -1 ){
		ustr = ustr.replace( /\%/gi, '\\' );
	}
	
	var i=0, len=s.length;
	for( ;i<len;i+=1 ){
		c = (s.substr(i, 1)).charCodeAt();
		if (c >= 0xAC00 && c <= 0xD7AF){ // 완성형 한글
			result = true;
		}else if( c >= 0x3130 && c <= 0x318F ) { // 완성형 아님
			result = false;
			break;
		}else{ // 한글아님
			result = false;
			break;
		};
	}
	return result;
}
/**
 * 아이디 정책
 * @param id
 * @returns {Boolean}
 */
function canUseUserIdPattern(id){
	
	var regExpId = /[^0-9^a-z]/g;
	var bTest = regExpId.test(id);
	
	return !bTest;
}

/**
 * @author 서승현
 * @description 레이어 팝업 화면의 가운데 위치로 조정 함수
 * @param {String} '.'을 제외한 클래스명
 * */
function funcPosiCenter(className){
	var w;
	var h;
	
	//다양한 브라우저에서 창크기를 가져옴
	if(typeof(window.innerWidth) == 'number' ) {
		 w = window.innerWidth;
		 h = window.innerHeight;
	}else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ){
		w = document.documentElement.clientWidth;
		h = document.documentElement.clientHeight;
	}else if(document.gody && (document.body.clientWidth || document.body.clientHeight ) ){
		w = document.body.clientWidth;
		h = document.body.clientHeight;
	}
	w= Number(w/2);
	h= Number(h/2);
	var classWidth =  Number($("."+className).outerWidth()/2);
	var classHeight =  Number($("."+className).outerHeight()/2+50);
	
	$("."+className).stop().animate({"left":(w-classWidth) + 'px',"top":(h-classHeight +$(window).scrollTop()) + 'px'}, "fast");
//	$("."+className).css({"left":(w-classWidth) + 'px',"top":(h-classHeight +$(window).scrollTop()) + 'px'});	//어두운 div가 창크기에 맞게 조절
}

//시간이 23을 넘는지 체크한다.
function timeChk(time)
{
	if(time != null && time != "")
	{
		if(parseInt(time) > 23)
		{
			return false;	
		}
		else
		{
			return true;
		}
	}
	else
	{
		return true;
	}
}

//분이 59을 넘는지 체크한다.
function minChk(min)
{
	if(min != null && min != "")
	{
		if(parseInt(min) > 59)
		{
			return false;	
		}
		else
		{
			return true;
		}
	}
	else
	{
		return true;
	}
}

//시간, 분을 2자리로 맞춤
function timeSet(str)
{
	if(str != null && str != "")
	{
		if(parseInt(str) < 10)
		{
			return "0" + parseInt(str); 
		}
	}
	return str;
}

/**
 * 2015.05.07, shseo
 * replaceAll 기능 추가
 * param ( 문장글, 문장글 안에 특정 단어, 교체할 단어)
 */
function replaceAll(str, searchStr, replaceStr) {
    return str.split(searchStr).join(replaceStr);
}



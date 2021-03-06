package com.gettoere.controller;

import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gettoere.service.JoinService;
import com.gettoere.vo.JoinVO;
import com.gettoere.vo.LoginVO;

@Controller
@RequestMapping("/join/*")
public class JoinController {
	@Inject
	private JoinService service;
	
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public void succuessGET(JoinVO vo, Model model) throws Exception {
		logger.info("signUp get ......");
	}

	@RequestMapping(value = "/success", method = RequestMethod.POST)
	public String succuessPOST(JoinVO vo, RedirectAttributes rttr) throws Exception {
		// return "/board/success";
		return "redirect:/";

	}

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public void joinGET(JoinVO vo, Model model) throws Exception {
		logger.info("signUp get ......");
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String joinPOST(JoinVO vo, RedirectAttributes rttr) throws Exception {
		service.regist(vo);

		rttr.addFlashAttribute("result", "success");

		return "/join/success";

	}

	/*
	 * @RequestMapping(value = "/UserRegisterCheck", method = RequestMethod.GET)
	 * public void idCheckGET(JoinVO vo, Model model) throws Exception {
	 * logger.info("IdCheckForm get ......"); model.addAttribute("idCheck",
	 * service.idCheck(vo));
	 * 
	 * }
	 */
	@RequestMapping(value = "/IdCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int idCheck(JoinVO vo) throws Exception {
		logger.info("IdCheckForm get ......");
		// System.out.println("controller:"+vo.getId());
		return service.idCheck(vo);
	}

	@RequestMapping(value = "/NicknameCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int nicknameCheck(JoinVO vo) throws Exception {
		logger.info("nickCheckForm get ......");
		// System.out.println("controller:"+vo.getNickname());
		return service.nicknameCheck(vo);
	}

	@RequestMapping(value = "/EmailCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int EmailCheck(JoinVO vo) throws Exception {
		logger.info("EmailCheck get ......");
		System.out.println("controller:"+vo.getEmail());
        return service.emailCheck(vo);
	}
	
	// ????????? ?????? ktg 17.11.30
	@RequestMapping(value = "/PhoneCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int phoneCheck(JoinVO vo) throws Exception {
		logger.info("PhoneCheckForm get ......");
		return service.phoneCheck(vo);
	}
	
	// ????????? ?????? ktg 17.11.24
	@RequestMapping(value = "/EmailConfirm", method = RequestMethod.POST)
	public @ResponseBody String EmailConfirmPost(JoinVO vo) throws Exception {
		logger.info("EmailConfirm Post ......");

		UUID uid = UUID.randomUUID();
		int start = (int) (Math.random() * 27);
		String confirmStr = "";

		confirmStr = uid.toString().replace("-", "").substring(start, start + 4);
		logger.info("UID : " + uid.toString().replace("-", "").substring(start, start + 4));
		vo.setTemp(confirmStr);
		
		mailSend(vo, "emailConfirm");
		
		logger.info("confirmStr Post ......" + confirmStr);
		return confirmStr;
	}

	@RequestMapping(value = "/idFind", method = RequestMethod.GET)
	public void idFindGET(JoinVO vo, Model model) throws Exception {
		logger.info("idFind get ......");

	}

	@RequestMapping(value = "/idFind", method = RequestMethod.POST)
	public String idFindPOST(JoinVO vo, RedirectAttributes rttr) throws Exception {
		service.idFind(vo);
		
		rttr.addFlashAttribute("result", "success");
		
		// return "/board/success";
		return "redirect:/board/list";
	}

	@RequestMapping(value = "/idFindfunction", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String idFindfunction(JoinVO vo) throws Exception {
		logger.info("idFindfunction get ......");
		return service.idFind(vo);
	}

	@RequestMapping(value = "/pwFind", method = RequestMethod.GET)
	public void pwFindGET(JoinVO vo, Model model) throws Exception {
		logger.info("pwFind get ......");
	}

	@RequestMapping(value = "/pwFind", method = RequestMethod.POST)
	public String pwFindPOST(JoinVO vo, RedirectAttributes rttr) throws Exception {
		service.pwFind(vo);
		
		rttr.addFlashAttribute("result", "success");
		
		// return "/board/success";
		return "redirect:/board/list";
		
	}
	
	@RequestMapping(value = "/pwFindfunction", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String pwFindfunction(JoinVO vo) throws Exception {
		logger.info("pwFindfunction get ......");
		
		return service.pwFind(vo);
	}
	
	// ???????????? ?????? ????????? ?????? ktg 17.11.27
	@RequestMapping(value = "/pwFindcheck", method = RequestMethod.POST)
	public @ResponseBody int pwFindPost(JoinVO vo) throws Exception {
		logger.info("pwFind Post ......");
		
		int userFindCheck = service.pwFindCheck(vo);
		logger.info("userFindCheck ......" + userFindCheck);
		// ????????? ????????? Member Table???  ?????? ??????
		if (userFindCheck == 1) {
			
			// ID, E-Mail ?????? ????????????
			service.pwFind(vo);
			
			// ????????? temp??? ??????
			UUID uid = UUID.randomUUID();
			String confirmStr = uid.toString();
			vo.setTemp(confirmStr);
			
			service.tempUp(vo);
			
			logger.info("ID : " + vo.getId());
			logger.info("E-Mail : " + vo.getEmail());
			logger.info("Temp : " + vo.getTemp());
			
			// ?????? ??????
			mailSend(vo, "findPw");
			
			return userFindCheck;
		}
		// ????????? ????????? Member Table???  ?????? ??????
		else {
			return userFindCheck;
		}
	}
	
	// ??? ???????????? ?????? ktg 17.11.27
	@RequestMapping(value = "/newPw", method = RequestMethod.GET)
	public void newPwGet(JoinVO vo, Model model) throws Exception {
		logger.info("newPw get ......");
	}
	
	// ??? ???????????? ?????? user?????? ?????? ktg 17.11.27
	@RequestMapping(value = "/usercheck", method = RequestMethod.POST)
	public @ResponseBody int usercheckPost(JoinVO vo) throws Exception {
		logger.info("userCheck Post ......");
		
		int userCheck = service.userCheck(vo);
		logger.info("userCheck ......" + userCheck);
		// ????????? ????????? Member Table???  ?????? ??????
		if (userCheck == 1) {
			logger.info("ID : " + vo.getId());
			logger.info("?????? Temp : " + vo.getTemp());
			
			// ??? ????????????, temp Update
			service.newPwUp(vo);
			
			// ????????? temp??? ??????
			UUID uid = UUID.randomUUID();
			String confirmStr = uid.toString();
			vo.setTemp(confirmStr);
			
			service.newTempUp(vo);
			logger.info("?????? PW : " + vo.getPw());
			logger.info("?????? Temp : " + vo.getTemp());
			
			return userCheck;
		}
		// ????????? ????????? Member Table???  ?????? ??????
		else {
			return userCheck;
		}
	}

	/*
	 * @RequestMapping(value = "/UserRegisterCheck", method = RequestMethod.POST)
	 * public String idCheckPOST(JoinVO vo , RedirectAttributes rttr) throws
	 * Exception{ service.idCheck(vo); rttr.addFlashAttribute("result","success");
	 * //return "/board/success"; return "redirect:/join/join";
	 * 
	 * }
	 */
	
	// ?????? ????????? ktg 17.11.23
	public void mailSend(JoinVO vo, String status) throws Exception {
		
		final String gId="gettoere@gmail.com"; // ?????? ?????????
	    final String gPw="rpxhfl1234"; // ?????? ??????
	    
	    String mailTitle = ""; // ?????? ??????
	    String mailContent = ""; // ?????? ??????
	    String userEmail = vo.getEmail(); // ?????????
	    
		Properties props = new Properties();
		/*
		 * props.put("mail.smtp.user",gId); props.put("mail.smtp.password",
		 * password);
		 */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gId, gPw);
			}
		});
		
		try {
			if (status.equals("findPw")) {
				// ?????? ??????
				mailTitle = vo.getId()+"???. ????????? ?????????."; // ??????
				mailContent = vo.getId()+"???" +
				"<br>????????? ??????????????? ?????????????????????! <br> ?????? ????????? ????????? ????????? ??????????????? ????????? ?????????." + 
				"<br><a href='http://192.168.0.56:8181/join/newPw?id=" + vo.getId() + "&temp=" + vo.getTemp() + "'>???___???</a>";
				// ?????? ?????? = ????????? ??????.jsp/join/newpw?id=vo.getId()&temp=vo.getTemp();
				
			} else if (status.equals("emailConfirm")) {
				// ???????????? ????????? ??????
				mailTitle = "????????? ???????????? ??????";
				mailContent = "<br>????????? ???????????? ????????? ?????? ?????????. <br> ?????? ????????? ?????? ?????? 4????????? ??????????????? ?????? ????????????.<br>" + 
				vo.getTemp();
			} else {
				logger.info("?????? ???????????? ?????????? ?????? ???????????? ???????????????!!");
			}
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gettoere@gmail.com"));// ????????? ???
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail)); // ?????? ???
			message.setSubject(mailTitle);// ??????
			//message.setText(mailContent);// ??????
			message.setContent(mailContent,"text/html; charset=utf-8");//???????????? html?????? charset??????
			Transport.send(message);
			System.out.println("SEND");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//?????????????????? kyo 17.11.30
	@RequestMapping(value = "/inFormUpdate", method = RequestMethod.GET)
	public void inFormUpdateGET(JoinVO vo, Model model, HttpServletRequest request) throws Exception {
		logger.info("signUp get ......");
		HttpSession session2 = request.getSession();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			model.addAttribute("inFormMsg", "loginFail");
		}
	}

	@RequestMapping(value = "/inFormUpdate", method = RequestMethod.POST)
	public String inFormUpdatePOST(JoinVO vo, HttpServletRequest request, RedirectAttributes rttr) throws Exception {

		service.Update(vo);
		
		// ?????? ??????. ssh. 171204
		HttpSession session2 = request.getSession();
		
		LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
		
		lVo.setNickname(vo.getNickname());
		lVo.setPhone(vo.getPhone());
		lVo.setEmail(vo.getEmail());
		
		session2.removeAttribute("LoginVo");
		
		// ????????? ??????. ssh. 171204
		session2.setAttribute("LoginVo", lVo);
    	
    	
		/*UUID uid = UUID.randomUUID();
		int start = (int) (Math.random() * 27);
		String confirmStr = "";

		confirmStr = uid.toString().replace("-", "").substring(start, start + 4);
		logger.info("UID : " + uid.toString().replace("-", "").substring(start, start + 4));
		vo.setTemp(confirmStr);
		
		mailSend(vo, "emailConfirm");*/
		rttr.addFlashAttribute("result", "success");
		

		return "/join/inFormUpSuccess";

	}
	
	//???????????? ?????? ??????????????? kyo 17.12.1
	@RequestMapping(value = "/PhoneUpCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int PhoneUpCheck(JoinVO vo , HttpServletRequest request) throws Exception {
		logger.info("PhoneCheckForm get ......");
		HttpSession session2 = request.getSession();
		LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
		int mNo = lVo.getmNo();
		
		vo.setmNo(mNo);
		return service.phoneUpCheck(vo);
	}
	
	//???????????? ?????? ????????????????????? kyo 17.12.1
	@RequestMapping(value = "/NicknameUpCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int NicknameUpCheck(JoinVO vo , HttpServletRequest request) throws Exception {
		logger.info("PhoneCheckForm get ......");
		HttpSession session2 = request.getSession();
		LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
		int mNo = lVo.getmNo();
		
		vo.setmNo(mNo);
		return service.NicknameUpCheck(vo);
	}
	
	//???????????? ?????? ????????????????????? kyo 17.12.1
	@RequestMapping(value = "/EmailUpCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int EmailUpCheck(JoinVO vo , HttpServletRequest request) throws Exception {
		logger.info("PhoneCheckForm get ......");
		HttpSession session2 = request.getSession();
		LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
		int mNo = lVo.getmNo();
		
		vo.setmNo(mNo);
		
		return service.EmailUpCheck(vo);
	}
	
}

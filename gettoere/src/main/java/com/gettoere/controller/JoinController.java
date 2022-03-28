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
	
	// 핸드폰 체크 ktg 17.11.30
	@RequestMapping(value = "/PhoneCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int phoneCheck(JoinVO vo) throws Exception {
		logger.info("PhoneCheckForm get ......");
		return service.phoneCheck(vo);
	}
	
	// 이메일 인증 ktg 17.11.24
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
	
	// 비밀번호 찾기 이메일 전송 ktg 17.11.27
	@RequestMapping(value = "/pwFindcheck", method = RequestMethod.POST)
	public @ResponseBody int pwFindPost(JoinVO vo) throws Exception {
		logger.info("pwFind Post ......");
		
		int userFindCheck = service.pwFindCheck(vo);
		logger.info("userFindCheck ......" + userFindCheck);
		// 입력한 정보가 Member Table에  있을 경우
		if (userFindCheck == 1) {
			
			// ID, E-Mail 정보 가져오기
			service.pwFind(vo);
			
			// 랜덤값 temp에 저장
			UUID uid = UUID.randomUUID();
			String confirmStr = uid.toString();
			vo.setTemp(confirmStr);
			
			service.tempUp(vo);
			
			logger.info("ID : " + vo.getId());
			logger.info("E-Mail : " + vo.getEmail());
			logger.info("Temp : " + vo.getTemp());
			
			// 메일 전송
			mailSend(vo, "findPw");
			
			return userFindCheck;
		}
		// 입력한 정보가 Member Table에  없을 경우
		else {
			return userFindCheck;
		}
	}
	
	// 새 비밀번호 설정 ktg 17.11.27
	@RequestMapping(value = "/newPw", method = RequestMethod.GET)
	public void newPwGet(JoinVO vo, Model model) throws Exception {
		logger.info("newPw get ......");
	}
	
	// 새 비밀번호 설정 user정보 확인 ktg 17.11.27
	@RequestMapping(value = "/usercheck", method = RequestMethod.POST)
	public @ResponseBody int usercheckPost(JoinVO vo) throws Exception {
		logger.info("userCheck Post ......");
		
		int userCheck = service.userCheck(vo);
		logger.info("userCheck ......" + userCheck);
		// 입력한 정보가 Member Table에  있을 경우
		if (userCheck == 1) {
			logger.info("ID : " + vo.getId());
			logger.info("기존 Temp : " + vo.getTemp());
			
			// 새 비밀번호, temp Update
			service.newPwUp(vo);
			
			// 랜덤값 temp에 저장
			UUID uid = UUID.randomUUID();
			String confirmStr = uid.toString();
			vo.setTemp(confirmStr);
			
			service.newTempUp(vo);
			logger.info("바뀐 PW : " + vo.getPw());
			logger.info("바뀐 Temp : " + vo.getTemp());
			
			return userCheck;
		}
		// 입력한 정보가 Member Table에  없을 경우
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
	
	// 메일 보내기 ktg 17.11.23
	public void mailSend(JoinVO vo, String status) throws Exception {
		
		final String gId="gettoere@gmail.com"; // 구글 아이디
	    final String gPw="rpxhfl1234"; // 구글 비번
	    
	    String mailTitle = ""; // 메일 제목
	    String mailContent = ""; // 메일 내용
	    String userEmail = vo.getEmail(); // 받는이
	    
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
				// 비번 찾기
				mailTitle = vo.getId()+"님. 게토리 입니다."; // 제목
				mailContent = vo.getId()+"님" +
				"<br>게토리 비밀번호를 잃어버리셨군요! <br> 아래 링크로 가셔서 새로운 비밀번호를 입력해 주세요." + 
				"<br><a href='http://192.168.0.56:8181/join/newPw?id=" + vo.getId() + "&temp=" + vo.getTemp() + "'>링___크</a>";
				// 내용 링크 = 새로운 비번.jsp/join/newpw?id=vo.getId()&temp=vo.getTemp();
				
			} else if (status.equals("emailConfirm")) {
				// 회원가입 이메일 인증
				mailTitle = "게토리 회원가입 인증";
				mailContent = "<br>게토리 회원가입 이메일 인증 입니다. <br> 아래 보이는 인증 번호 4자리를 복사하거나 입력 해주세요.<br>" + 
				vo.getTemp();
			} else {
				logger.info("뭔가 잘못된게 있을까? 그냥 파라미터 안받은거지!!");
			}
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gettoere@gmail.com"));// 보내는 이
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail)); // 받는 이
			message.setSubject(mailTitle);// 제목
			//message.setText(mailContent);// 내용
			message.setContent(mailContent,"text/html; charset=utf-8");//글내용을 html타입 charset설정
			Transport.send(message);
			System.out.println("SEND");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//개인정보수정 kyo 17.11.30
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
		
		// 세션 변경. ssh. 171204
		HttpSession session2 = request.getSession();
		
		LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
		
		lVo.setNickname(vo.getNickname());
		lVo.setPhone(vo.getPhone());
		lVo.setEmail(vo.getEmail());
		
		session2.removeAttribute("LoginVo");
		
		// 세션에 저장. ssh. 171204
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
	
	//개인정보 수정 폰중복체크 kyo 17.12.1
	@RequestMapping(value = "/PhoneUpCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int PhoneUpCheck(JoinVO vo , HttpServletRequest request) throws Exception {
		logger.info("PhoneCheckForm get ......");
		HttpSession session2 = request.getSession();
		LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
		int mNo = lVo.getmNo();
		
		vo.setmNo(mNo);
		return service.phoneUpCheck(vo);
	}
	
	//개인정보 수정 닉네임중복체크 kyo 17.12.1
	@RequestMapping(value = "/NicknameUpCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int NicknameUpCheck(JoinVO vo , HttpServletRequest request) throws Exception {
		logger.info("PhoneCheckForm get ......");
		HttpSession session2 = request.getSession();
		LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
		int mNo = lVo.getmNo();
		
		vo.setmNo(mNo);
		return service.NicknameUpCheck(vo);
	}
	
	//개인정보 수정 이메일중복체크 kyo 17.12.1
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

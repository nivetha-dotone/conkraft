package com.wfd.dot1.cwfm.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.ChatRequest;
import com.wfd.dot1.cwfm.dto.ChatResponse;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.ChatBotService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping("/chatbot/ask")
    @ResponseBody
    public ChatResponse askQuestion(@RequestBody ChatRequest request, HttpServletRequest req,HttpServletResponse response
                                    ){

    	HttpSession session = req.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        request.setSessionId(session.getId());
        request.setUser(user);
        //Object user=session.getAttribute("USERID");

        if(user!=null){
            request.setUserId(String.valueOf(user.getUserId()));
        }

        //Object pe=session.getAttribute("PEID");

        //if(pe!=null){
        //    request.setPrincipalEmployerId(pe.toString());
       // }

        return chatBotService.processMessage(request);

    }
    
    @GetMapping("/showChatBot")
    public String showChatBot(
            HttpServletRequest request,
            Model model) {

        HttpSession session =
                request.getSession(false);

        MasterUser user =
                session != null
                        ? (MasterUser) session.getAttribute("loginuser")
                        : null;

        String firstName = "User";

        if (user != null
                && user.getFirstName() != null
                && !user.getFirstName().trim().isEmpty()) {

            firstName = user.getFirstName().trim();
        }

        model.addAttribute("firstName", firstName);

        return "chatbot/chatbot";
    }
	

}

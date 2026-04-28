package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.PlantWorkmenDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.DashboardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/dashboardPage")
    public String dashboardPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        if (user == null) {
            return "redirect:/login";
        }

        // 🔥 Fetch dashboard data
        DashboardDTO dashboard = service.getDashboard(user);

        // Store in session (optional reuse)
        session.setAttribute("dashboardData", dashboard);
        model.addAttribute("dashboard", dashboard);

        // 🔥 ROLE HANDLING (IMPORTANT FIX)
        String role = user.getRoleName() == null ? "" : user.getRoleName().trim().toUpperCase();

        boolean isContractorSupervisor = role.contains("CONTRACTOR") ;
        boolean isHR = role.contains("HR");
        boolean isSecurity = role.contains("SECURITY");
        boolean isMedical = role.contains("MEDICAL");
        boolean isSafety = role.contains("SAFETY");
        boolean isEIC = role.contains("EIC") || role.contains("ENGINEER-IN-CHARGE");
        boolean isSystemAdmin = role.contains("SYSTEM ADMIN");

        // 🔥 Send flags to JSP
        model.addAttribute("isHR", isHR);
        model.addAttribute("isSecurity", isSecurity);
        model.addAttribute("isMedical", isMedical);
        model.addAttribute("isSafety", isSafety);
        model.addAttribute("isEIC", isEIC);
        model.addAttribute("isSystemAdmin", isSystemAdmin);

        // 🔥 Decide which JSP to load
        if (isContractorSupervisor) {
            return "dashboard/csDashboard";
        } else {
            return "dashboard/roleDashboard";
        }
    }

    @GetMapping("/getDashboardCharts")
    @ResponseBody
    public Map<String, Object> getCharts(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        DashboardDTO dto = session != null ? (DashboardDTO) session.getAttribute("dashboardData") : null;

        Map<String, Object> map = new HashMap<>();

        List<String> plantLabels = new ArrayList<>();
        List<Integer> plantData = new ArrayList<>();

        if (dto != null && dto.getPlantWorkmenList() != null) {
            for (PlantWorkmenDTO p : dto.getPlantWorkmenList()) {
                plantLabels.add(p.getPlantName());
                plantData.add(p.getActiveCount());
            }
        }

        map.put("plantLabels", plantLabels);
        map.put("plantData", plantData);

        return map;
    }

    @GetMapping("/getWorkmenByWO")
    @ResponseBody
    public List<WorkOrderDTO> getWorkmenByWO(@RequestParam("woId") long woId,
                                             @RequestParam("contractorId") String contractorId) {
        return service.getWorkmenByWO(woId, contractorId);
    }
}
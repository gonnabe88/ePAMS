package com.kdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdb.dto.MemberDTO;
import com.kdb.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;
    
    @PostMapping("/id-check")
    public @ResponseBody String idCheck(@RequestParam("userId") String userId) {
        System.out.println("userId = " + userId);
        String checkResult = memberService.idCheck(userId);
        return checkResult;
    }
    
    @PostMapping("/login")
    public @ResponseBody String login(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("login userId = " + memberDTO.toString());
        String checkResult = memberService.auth(memberDTO);
        return checkResult;
    }
    
    
    @PostMapping("/auth-login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        String loginResult = memberService.login(memberDTO);

        
        System.out.println("login");
        if (loginResult == "1") {
            //session.setAttribute("loginId", memberDTO.getuserId());4
        	
        	System.out.println("login success!!");
            return "auth-login";
        } else {
        	System.out.println("login fail!!");
            return "auth-login";
        }
    }

	@GetMapping("/test")
	public String test() {
		return "test";
	}
    @GetMapping(value = {"/", "/login"})
    public String index() {
        return "auth-login";
    }
    @GetMapping(value = {"/index", "/index.html" })
	public String indexMain() {
    	return "index";
    }    	

	@GetMapping("/application-chat")
	public String applicationChat() {
		return "application-chat";
	}

	@GetMapping("/application-checkout")
	public String applicationCheckout() {
		return "application-checkout";
	}

	@GetMapping("/application-email")
	public String applicationEmail() {
		return "application-email";
	}

	@GetMapping("/application-gallery")
	public String applicationGallery() {
		return "application-gallery";
	}

	@GetMapping("/error-403")
	public String error403() {
		return "error-403";
	}	

	@GetMapping("/error-404")
	public String error404() {
		return "error-404";
	}

	@GetMapping("/error-500")
	public String error500() {
		return "error-500";
	}

	@GetMapping("/auth-login")
	public String authLogin() {
		return "auth-login";
	}

	@GetMapping("/auth-register")
	public String authRegister() {
		return "auth-register";
	}

	@GetMapping("/auth-forgot-password")
	public String authForgotPassword() {
		return "auth-forgot-password";
	}

	@GetMapping("/component-accordion")
	public String componentAccordion() {
		return "component-accordion";
	}

	@GetMapping("/component-alert")
	public String componentAlert() {
		return "component-alert";
	}

	@GetMapping("/component-badge")
	public String componentBadge() {
		return "component-badge";
	}

	@GetMapping("/component-breadcrumb")
	public String componentBreadcrumb() {
		return "component-breadcrumb";
	}

	@GetMapping("/component-button")
	public String componentButton() {
		return "component-button";
	}

	@GetMapping("/component-card")
	public String componentCard() {
		return "component-card";
	}

	@GetMapping("/component-carousel")
	public String componentCarousel() {
		return "component-carousel";
	}

	@GetMapping("/component-collapse")
	public String componentCollapse() {
		return "component-collapse";
	}

	@GetMapping("/component-dropdown")
	public String componentDropdown() {
		return "component-dropdown";
	}

	@GetMapping("/component-list-group")
	public String componentListGroup() {
		return "component-list-group";
	}

	@GetMapping("/component-modal")
	public String componentModal() {
		return "component-modal";
	}

	@GetMapping("/component-navs")
	public String componentNavs() {
		return "component-navs";
	}

	@GetMapping("/component-pagination")
	public String componentPagination() {
		return "component-pagination";
	}

	@GetMapping("/component-progress")
	public String componentProgress() {
		return "component-progress";
	}

	@GetMapping("/component-spinner")
	public String componentSpinner() {
		return "component-spinner";
	}

	@GetMapping("/component-toasts")
	public String componentToasts() {
		return "component-toasts";
	}

	@GetMapping("/component-tooltip")
	public String componentTooltip() {
		return "component-tooltip";
	}

	@GetMapping("/extra-component-avatar")
	public String extraComponentAvatar() {
		return "extra-component-avatar";
	}

	@GetMapping("/extra-component-date-picker")
	public String extraComponentDatePicker() {
		return "extra-component-date-picker";
	}

	@GetMapping("/extra-component-divider")
	public String extraComponentDivider() {
		return "extra-component-divider";
	}

	@GetMapping("/extra-component-rating")
	public String extraComponentRating() {
		return "extra-component-rating";
	}

	@GetMapping("/extra-component-sweetalert")
	public String extraComponentSweetalert() {
		return "extra-component-sweetalert";
	}

	@GetMapping("/extra-component-toastify")
	public String extraComponentToastify() {
		return "extra-component-toastify";
	}

	@GetMapping("/layout-default")
	public String layoutDefault() {
		return "layout-default";
	}

	@GetMapping("/layout-horizontal")
	public String layoutHorizontal() {
		return "layout-horizontal";
	}

	@GetMapping("/layout-rtl")
	public String layoutRtl() {
		return "layout-rtl";
	}

	@GetMapping("/layout-rtl-backup")
	public String layoutRtlBackup() {
		return "layout-rtl-backup";
	}

	@GetMapping("/layout-vertical-1-column")
	public String layoutVertical1Column() {
		return "layout-vertical-1-column";
	}

	@GetMapping("/layout-vertical-navbar")
	public String layoutVerticalNavbar() {
		return "layout-vertical-navbar";
	}

	@GetMapping("/form-editor-ckeditor")
	public String formEditorCkeditor() {
		return "form-editor-ckeditor";
	}

	@GetMapping("/form-editor-quill")
	public String formEditorQuill() {
		return "form-editor-quill";
	}

	@GetMapping("/form-editor-summernote")
	public String formEditorSummernote() {
		return "form-editor-summernote";
	}

	@GetMapping("/form-editor-tinymce")
	public String formEditorTinymce() {
		return "form-editor-tinymce";
	}

	@GetMapping("/form-element-checkbox")
	public String formElementCheckbox() {
		return "form-element-checkbox";
	}

	@GetMapping("/form-element-input-group")
	public String formElementInputGroup() {
		return "form-element-input-group";
	}

	@GetMapping("/form-element-input")
	public String formElementInput() {
		return "form-element-input";
	}

	@GetMapping("/form-element-radio")
	public String formElementRadio() {
		return "form-element-radio";
	}

	@GetMapping("/form-element-select")
	public String formElementSelect() {
		return "form-element-select";
	}

	@GetMapping("/form-element-textarea")
	public String formElementTextarea() {
		return "form-element-textarea";
	}

	@GetMapping("/form-layout")
	public String formLayout() {
		return "form-layout";
	}

	@GetMapping("/form-validation-parsley")
	public String formValidationParsley() {
		return "form-validation-parsley";
	}

	@GetMapping("/table")
	public String table() {
		return "table";
	}

	@GetMapping("/table-datatable")
	public String tableDatatable() {
		return "table-datatable";
	}

	@GetMapping("/table-datatable-jquery")
	public String tableDatatableJquery() {
		return "table-datatable-jquery";
	}

	@GetMapping("/ui-chart-apexcharts")
	public String uiChartApexcharts() {
		return "ui-chart-apexcharts";
	}

	@GetMapping("/ui-chart-chartjs")
	public String uiChartChartjs() {
		return "ui-chart-chartjs";
	}

	@GetMapping("/ui-file-uploader")
	public String uiFileUploader() {
		return "ui-file-uploader";
	}

	@GetMapping("/ui-icons-bootstrap-icons")
	public String uiIconsBootstrapIcons() {
		return "ui-icons-bootstrap-icons";
	}

	@GetMapping("/ui-icons-dripicons")
	public String uiIconsDripicons() {
		return "ui-icons-dripicons";
	}

	@GetMapping("/ui-icons-fontawesome")
	public String uiIconsFontawesome() {
		return "ui-icons-fontawesome";
	}

	@GetMapping("/ui-map-google-map")
	public String uiMapGoogleMap() {
		return "ui-map-google-map";
	}

	@GetMapping("/ui-map-jsvectormap")
	public String uiMapJsvectormap() {
		return "ui-map-jsvectormap";
	}

	@GetMapping("/ui-multi-level-menu")
	public String uiMultiLevelMenu() {
		return "ui-multi-level-menu";
	}

	@GetMapping("/ui-widgets-chatbox")
	public String uiWidgetsChatbox() {
		return "ui-widgets-chatbox";
	}

	@GetMapping("/ui-widgets-pricing")
	public String uiWidgetsPricing() {
		return "ui-widgets-pricing";
	}

	@GetMapping("/ui-widgets-todolist")
	public String uiWidgetsTodolist() {
		return "ui-widgets-todolist";
	}
}

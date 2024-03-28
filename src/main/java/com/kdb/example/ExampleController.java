package com.kdb.example;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.kdb.example.member.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExampleController {

	
	@GetMapping("/auth-register")
	public String authRegister(@ModelAttribute("memberDTO") MemberDTO memberDTO) {
		return "/sample/auth-register";
	}

	@GetMapping("/application-chat")
	public String applicationChat() {
		return "/sample/application-chat";
	}

	@GetMapping("/application-checkout")
	public String applicationCheckout() {
		return "/sample/application-checkout";
	}

	@GetMapping("/application-email")
	public String applicationEmail() {
		return "/sample/application-email";
	}

	@GetMapping("/application-gallery")
	public String applicationGallery() {
		return "/sample/application-gallery";
	}

	@GetMapping("/error-403")
	public String error403() {
		return "/sample/error-403";
	}	

	@GetMapping("/error-404")
	public String error404() {
		return "/sample/error-404";
	}

	@GetMapping("/error-500")
	public String error500() {
		return "/sample/error-500";
	}

	@GetMapping("/auth-forgot-password")
	public String authForgotPassword() {
		return "/sample/auth-forgot-password";
	}

	@GetMapping("/component-accordion")
	public String componentAccordion() {
    
            return "/sample/component-accordion";
    }

	@GetMapping("/component-alert")
	public String componentAlert() {
		return "/sample/component-alert";
	}

	@GetMapping("/component-badge")
	public String componentBadge() {
		return "/sample/component-badge";
	}

	@GetMapping("/component-breadcrumb")
	public String componentBreadcrumb() {
		return "/sample/component-breadcrumb";
	}

	@GetMapping("/component-button")
	public String componentButton() {
		return "/sample/component-button";
	}

	@GetMapping("/component-card")
	public String componentCard() {
		return "/sample/component-card";
	}

	@GetMapping("/component-carousel")
	public String componentCarousel() {
		return "/sample/component-carousel";
	}

	@GetMapping("/component-collapse")
	public String componentCollapse() {
		return "/sample/component-collapse";
	}

	@GetMapping("/component-dropdown")
	public String componentDropdown() {
		return "/sample/component-dropdown";
	}

	@GetMapping("/component-list-group")
	public String componentListGroup() {
		return "/sample/component-list-group";
	}

	@GetMapping("/component-modal")
	public String componentModal() {
		return "/sample/component-modal";
	}

	@GetMapping("/component-navs")
	public String componentNavs() {
		return "/sample/component-navs";
	}

	@GetMapping("/component-pagination")
	public String componentPagination() {
		return "/sample/component-pagination";
	}

	@GetMapping("/component-progress")
	public String componentProgress() {
		return "/sample/component-progress";
	}

	@GetMapping("/component-spinner")
	public String componentSpinner() {
		return "/sample/component-spinner";
	}

	@GetMapping("/component-toasts")
	public String componentToasts() {
		return "/sample/component-toasts";
	}

	@GetMapping("/component-tooltip")
	public String componentTooltip() {
		return "/sample/component-tooltip";
	}

	@GetMapping("/extra-component-avatar")
	public String extraComponentAvatar() {
		return "/sample/extra-component-avatar";
	}

	@GetMapping("/extra-component-date-picker")
	public String extraComponentDatePicker() {
		return "/sample/extra-component-date-picker";
	}

	@GetMapping("/extra-component-divider")
	public String extraComponentDivider() {
		return "/sample/extra-component-divider";
	}

	@GetMapping("/extra-component-rating")
	public String extraComponentRating() {
		return "/sample/extra-component-rating";
	}

	@GetMapping("/extra-component-sweetalert")
	public String extraComponentSweetalert() {
		return "/sample/extra-component-sweetalert";
	}

	@GetMapping("/extra-component-toastify")
	public String extraComponentToastify() {
		return "/sample/extra-component-toastify";
	}

	@GetMapping("/layout-default")
	public String layoutDefault() {
		return "/sample/layout-default";
	}

	@GetMapping("/layout-horizontal")
	public String layoutHorizontal() {
		return "/sample/layout-horizontal";
	}

	@GetMapping("/layout-rtl")
	public String layoutRtl() {
		return "/sample/layout-rtl";
	}

	@GetMapping("/layout-rtl-backup")
	public String layoutRtlBackup() {
		return "/sample/layout-rtl-backup";
	}

	@GetMapping("/layout-vertical-1-column")
	public String layoutVertical1Column() {
		return "/sample/layout-vertical-1-column";
	}

	@GetMapping("/layout-vertical-navbar")
	public String layoutVerticalNavbar() {
		return "/sample/layout-vertical-navbar";
	}

	@GetMapping("/form-editor-ckeditor")
	public String formEditorCkeditor() {
		return "/sample/form-editor-ckeditor";
	}

	@GetMapping("/form-editor-quill")
	public String formEditorQuill() {
		return "/sample/form-editor-quill";
	}

	@GetMapping("/form-editor-summernote")
	public String formEditorSummernote() {
		return "/sample/form-editor-summernote";
	}

	@GetMapping("/form-editor-tinymce")
	public String formEditorTinymce() {
		return "/sample/form-editor-tinymce";
	}

	@GetMapping("/form-element-checkbox")
	public String formElementCheckbox() {
		return "/sample/form-element-checkbox";
	}

	@GetMapping("/form-element-input-group")
	public String formElementInputGroup() {
		return "/sample/form-element-input-group";
	}

	@GetMapping("/form-element-input")
	public String formElementInput() {
		return "/sample/form-element-input";
	}

	@GetMapping("/form-element-radio")
	public String formElementRadio() {
		return "/sample/form-element-radio";
	}

	@GetMapping("/form-element-select")
	public String formElementSelect() {
		return "/sample/form-element-select";
	}

	@GetMapping("/form-element-textarea")
	public String formElementTextarea() {
		return "/sample/form-element-textarea";
	}

	@GetMapping("/form-layout")
	public String formLayout() {
		return "/sample/form-layout";
	}

	@GetMapping("/form-validation-parsley")
	public String formValidationParsley() {
		return "/sample/form-validation-parsley";
	}

	@GetMapping("/table")
	public String table() {
		return "/sample/table";
	}

	@GetMapping("/table-datatable")
	public String tableDatatable() {
		return "/sample/table-datatable";
	}

	@GetMapping("/table-datatable-jquery")
	public String tableDatatableJquery() {
		return "/sample/table-datatable-jquery";
	}

	@GetMapping("/ui-chart-apexcharts")
	public String uiChartApexcharts() {
		return "/sample/ui-chart-apexcharts";
	}

	@GetMapping("/ui-chart-chartjs")
	public String uiChartChartjs() {
		return "/sample/ui-chart-chartjs";
	}

	@GetMapping("/ui-file-uploader")
	public String uiFileUploader() {
		return "/sample/ui-file-uploader";
	}

	@GetMapping("/ui-icons-bootstrap-icons")
	public String uiIconsBootstrapIcons() {
		return "/sample/ui-icons-bootstrap-icons";
	}

	@GetMapping("/ui-icons-dripicons")
	public String uiIconsDripicons() {
		return "/sample/ui-icons-dripicons";
	}

	@GetMapping("/ui-icons-fontawesome")
	public String uiIconsFontawesome() {
		return "/sample/ui-icons-fontawesome";
	}

	@GetMapping("/ui-map-google-map")
	public String uiMapGoogleMap() {
		return "/sample/ui-map-google-map";
	}

	@GetMapping("/ui-map-jsvectormap")
	public String uiMapJsvectormap() {
		return "/sample/ui-map-jsvectormap";
	}

	@GetMapping("/ui-multi-level-menu")
	public String uiMultiLevelMenu() {
		return "/sample/ui-multi-level-menu";
	}

	@GetMapping("/ui-widgets-chatbox")
	public String uiWidgetsChatbox() {
		return "/sample/ui-widgets-chatbox";
	}

	@GetMapping("/ui-widgets-pricing")
	public String uiWidgetsPricing() {
		return "/sample/ui-widgets-pricing";
	}

	@GetMapping("/ui-widgets-todolist")
	public String uiWidgetsTodolist() {
		return "/sample/ui-widgets-todolist";
	}
	 
}

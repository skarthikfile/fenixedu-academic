package net.sourceforge.fenixedu.presentationTier.Action.student.candidacy;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.domain.candidacy.CandidacyInformationBean;
import net.sourceforge.fenixedu.domain.candidacy.PersonalInformationBean;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.injectionCode.AccessControl;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixDispatchAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.renderers.validators.RegexpValidator;
import pt.ist.fenixWebFramework.struts.annotations.Forward;
import pt.ist.fenixWebFramework.struts.annotations.Forwards;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/editMissingCandidacyInformation", module = "student")
@Forwards({

@Forward(name = "editMissingPersonalInformation", path = "candidacy.edit.missing.candidacy.information")

})
public class EditMissingCandidacyInformationDA extends FenixDispatchAction {

    public static class ConclusionGradeRegexpValidator extends RegexpValidator {
	public ConclusionGradeRegexpValidator() {
	    super();
	    setRegexp("(\\d{2})?");
	    setMessage("error.CandidacyInformationBean.conclusionGrade.invalid.format");
	    setKey(true);
	    setBundle("APPLICATION_RESOURCES");
	}
    }

    public static class ConclusionYearRegexpValidator extends RegexpValidator {
	public ConclusionYearRegexpValidator() {
	    super();
	    setRegexp("(\\d{4})?");
	    setMessage("error.CandidacyInformationBean.conclusionYear.invalid.format");
	    setKey(true);
	    setBundle("APPLICATION_RESOURCES");
	}
    }

    public static class NumberOfCandidaciesRegexpValidator extends RegexpValidator {
	public NumberOfCandidaciesRegexpValidator() {
	    super();
	    setRegexp("(\\d{1,2})?");
	    setMessage("error.CandidacyInformationBean.numberOfCandidaciesToHigherSchool.invalid.format");
	    setKey(true);
	    setBundle("APPLICATION_RESOURCES");
	}
    }

    public static class NumberOfFlunksRegexpValidator extends RegexpValidator {
	public NumberOfFlunksRegexpValidator() {
	    super();
	    setRegexp("(\\d{1,2})?");
	    setMessage("error.CandidacyInformationBean.numberOfFlunksOnHighSchool.invalid.format");
	    setKey(true);
	    setBundle("APPLICATION_RESOURCES");
	}
    }

    static protected List<PersonalInformationBean> getPersonalInformationsWithMissingInfo() {
	return AccessControl.getPerson().getStudent().getPersonalInformationsWithMissingInformation();
    }

    static protected List<CandidacyInformationBean> getCandidacyInformationsWithMissingInfo() {
	return AccessControl.getPerson().getStudent().getCandidacyInformationsWithMissingInformation();
    }

    public ActionForward prepareEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	final List<PersonalInformationBean> list = getPersonalInformationsWithMissingInfo();

	request.setAttribute("personalInformationsWithMissingInformation", list);
	request.setAttribute("personalInformationBean", list.get(0));

	return mapping.findForward("editMissingPersonalInformation");
    }

    public ActionForward prepareEditInvalid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	request.setAttribute("personalInformationsWithMissingInformation", getPersonalInformationsWithMissingInfo());
	request.setAttribute("personalInformationBean", getRenderedObject("personalInformationBean"));

	return mapping.findForward("editMissingPersonalInformation");
    }

    public ActionForward prepareEditPostback(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	request.setAttribute("personalInformationsWithMissingInformation", getPersonalInformationsWithMissingInfo());
	request.setAttribute("personalInformationBean", getRenderedObject("personalInformationBean"));
	RenderUtils.invalidateViewState();

	return mapping.findForward("editMissingPersonalInformation");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

	final PersonalInformationBean personalInformationBean = getRenderedObject("personalInformationBean");

	final Set<String> messages = personalInformationBean.validate();

	if (!messages.isEmpty()) {
	    for (final String each : messages) {
		addActionMessage(request, each);
	    }
	    return prepareEditInvalid(mapping, form, request, response);
	}

	try {
	    personalInformationBean.updatePersonalInformation();
	} catch (DomainException e) {
	    addActionMessage(request, e.getKey(), e.getArgs());
	    return prepareEditInvalid(mapping, form, request, response);
	}

	final ActionForward forward = new ActionForward();
	forward.setPath("/home.do");
	forward.setRedirect(true);
	forward.setModule("/");

	return forward;

    }
}

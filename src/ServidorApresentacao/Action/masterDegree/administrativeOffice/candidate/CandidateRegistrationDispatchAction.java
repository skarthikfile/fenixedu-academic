/*
 * Created on 14/Mar/2003
 *
 */
package ServidorApresentacao.Action.masterDegree.administrativeOffice.candidate;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import DataBeans.InfoCandidateRegistration;
import DataBeans.InfoExecutionDegree;
import DataBeans.InfoMasterDegreeCandidate;
import ServidorAplicacao.GestorServicos;
import ServidorAplicacao.IUserView;
import ServidorAplicacao.Servico.exceptions.ActiveStudentCurricularPlanAlreadyExistsServiceException;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorAplicacao.Servico.exceptions.InvalidChangeServiceException;
import ServidorAplicacao.Servico.exceptions.NonExistingServiceException;
import ServidorApresentacao.Action.exceptions.ActiveStudentCurricularPlanAlreadyExistsActionException;
import ServidorApresentacao.Action.exceptions.FenixActionException;
import ServidorApresentacao.Action.exceptions.InvalidChangeActionException;
import ServidorApresentacao.Action.exceptions.NonExistingActionException;
import ServidorApresentacao.Action.sop.utils.SessionConstants;

/**
 * 
 * @author Nuno Nunes (nmsn@rnl.ist.utl.pt)
 *         Joana Mota (jccm@rnl.ist.utl.pt)
 * 
 * 
 */
public class CandidateRegistrationDispatchAction extends DispatchAction {

	public ActionForward getCandidateList(ActionMapping mapping, ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response)
		throws Exception {

	
		
		HttpSession session = request.getSession(false);

		DynaActionForm candidateRegistration = (DynaActionForm) form;

		String executionYearString = request.getParameter("executionYear");
		String degreeCode = request.getParameter("degree");
		
		if ((executionYearString == null) || (executionYearString.length() == 0)){
			executionYearString = (String) candidateRegistration.get("executionYear");
		}
		
		if ((degreeCode == null) || (degreeCode.length() == 0)){
			degreeCode = (String) candidateRegistration.get("degreeCode");
		}

		
		GestorServicos serviceManager = GestorServicos.manager();
		
		IUserView userView = (IUserView) session.getAttribute(SessionConstants.U_VIEW);
		
		List result = null;
		
		try {
			Object args[] = { executionYearString, degreeCode };
			result = (List) serviceManager.executar(userView, "ReadCandidateForRegistration", args);
		} catch (NonExistingServiceException e) {
			ActionErrors errors = new ActionErrors();
			errors.add("nonExisting", new ActionError("error.candidatesNotFound"));
			saveErrors(request, errors);
			return mapping.getInputForward();	
		}


		BeanComparator nameComparator = new BeanComparator("infoPerson.nome");
		Collections.sort(result, nameComparator);


		request.setAttribute("candidateList", result);
		candidateRegistration.set("degreeCode", degreeCode);
		candidateRegistration.set("executionYear", executionYearString);
		candidateRegistration.set("candidateID", null);
		

		InfoExecutionDegree infoExecutionDegree = null;
		try {
			Object args[] = { executionYearString, degreeCode };
			infoExecutionDegree = (InfoExecutionDegree) serviceManager.executar(userView, "ReadExecutionDegreeByExecutionYearAndDegreeCode", args);
		} catch (NonExistingServiceException e) {
			throw new NonExistingActionException(e);
		}
	
		request.setAttribute("infoExecutionDegree", infoExecutionDegree);
					
		return mapping.findForward("ListCandidates");
	}


	public ActionForward prepareCandidateRegistration(ActionMapping mapping, ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response)
		throws Exception {

	
		
		HttpSession session = request.getSession(false);

		DynaActionForm candidateRegistration = (DynaActionForm) form;

		GestorServicos serviceManager = GestorServicos.manager();
		
		IUserView userView = (IUserView) session.getAttribute(SessionConstants.U_VIEW);
		Integer candidateID = new Integer(request.getParameter("candidateID"));
		
		candidateRegistration.set("candidateID", candidateID);		


		List branchList = null;
		try {
			Object args[] = { candidateID };
			branchList = (List) serviceManager.executar(userView, "GetBranchListByCandidateID", args);
		} catch (FenixServiceException e) {
			throw new FenixActionException(e);
		}
					
		request.setAttribute("branchList", branchList);					
					
					
		InfoMasterDegreeCandidate infoMasterDegreeCandidate = null;					
		try {
			Object args[] = { candidateID };
			infoMasterDegreeCandidate = (InfoMasterDegreeCandidate) serviceManager.executar(userView, "GetCandidatesByID", args);
		} catch (NonExistingServiceException e) {
			throw new FenixActionException(e);
		}
		candidateRegistration.set("branchID", null);
		request.setAttribute("infoMasterDegreeCandidate", infoMasterDegreeCandidate);
				
		return mapping.findForward("ShowConfirmation");
	}
	  

	public ActionForward confirm(ActionMapping mapping, ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response)
		throws Exception {

	
		
		HttpSession session = request.getSession(false);

		DynaActionForm candidateRegistration = (DynaActionForm) form;

		GestorServicos serviceManager = GestorServicos.manager();
		
		IUserView userView = (IUserView) session.getAttribute(SessionConstants.U_VIEW);
		Integer candidateID = (Integer) candidateRegistration.get("candidateID");
		Integer branchID = (Integer) candidateRegistration.get("branchID");
		
		if ((request.getParameter("OK") != null) &&	(request.getParameter("notOK") == null)) {				

			InfoCandidateRegistration infoCandidateRegistration = null;
			try {
				Object args[] = { candidateID , branchID};
				infoCandidateRegistration = (InfoCandidateRegistration) serviceManager.executar(userView, "RegisterCandidate", args);
			} catch (ActiveStudentCurricularPlanAlreadyExistsServiceException e) {
				throw new ActiveStudentCurricularPlanAlreadyExistsActionException(e);
			} catch (InvalidChangeServiceException e) {
				throw new InvalidChangeActionException("error.cantRegisterCandidate", e);
			}catch (FenixServiceException e) {
				throw new FenixActionException(e);
			}

			candidateRegistration.set("studentCurricularPlanID", infoCandidateRegistration.getInfoStudentCurricularPlan().getIdInternal());
			request.setAttribute("infoCandidateRegistration", infoCandidateRegistration);

			return mapping.findForward("ShowResult");
		} else {
			return mapping.findForward("PrepareCandidateList");
		}
	}
	
	public ActionForward preparePrint(ActionMapping mapping, ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response)
		throws Exception {

	
		
		HttpSession session = request.getSession(false);

		DynaActionForm candidateRegistration = (DynaActionForm) form;

		GestorServicos serviceManager = GestorServicos.manager();
		
		IUserView userView = (IUserView) session.getAttribute(SessionConstants.U_VIEW);
		Integer candidateID = (Integer) candidateRegistration.get("candidateID");
		
		InfoCandidateRegistration infoCandidateRegistration = null;
		try {
			Object args[] = { candidateID };
			infoCandidateRegistration = (InfoCandidateRegistration) serviceManager.executar(userView, "GetCandidateRegistrationInformation", args);
		} catch (FenixServiceException e) {
			throw new FenixActionException(e);
		}

		request.setAttribute("infoCandidateRegistration", infoCandidateRegistration);
		request.setAttribute("infoExecutionDegree", infoCandidateRegistration.getInfoMasterDegreeCandidate().getInfoExecutionDegree());

		return mapping.findForward("Print");
	}	
	
}

/*
 * Created on 29/Jul/2003
 */
package net.sourceforge.fenixedu.domain.onlineTests;

import java.util.List;

import net.sourceforge.fenixedu.util.tests.CorrectionFormula;

/**
 * @author Susana Fernandes
 */
public class TestQuestion extends TestQuestion_Base {

    private CorrectionFormula formula;

    public CorrectionFormula getCorrectionFormula() {
        return formula;
    }

    public void setCorrectionFormula(CorrectionFormula formula) {
        this.formula = formula;
    }

    public void editTestQuestion(Integer newTestQuestionOrder, final Double newTestQuestionValue, final CorrectionFormula newFormula) {
        organizeTestQuestionsOrder(newTestQuestionOrder, this.getTestQuestionOrder());
        this.setTestQuestionOrder(newTestQuestionOrder);
        this.setTestQuestionValue(newTestQuestionValue);
        this.setCorrectionFormula(newFormula);
    }

    private void organizeTestQuestionsOrder(Integer newOrder, Integer oldOrder) {
        List<ITestQuestion> testQuestions = getTest().getTestQuestions();
        int diffOrder = newOrder.intValue() - oldOrder.intValue();
        if (diffOrder != 0) {
            if (diffOrder > 0) {
                for (ITestQuestion testQuestion : testQuestions) {
                    if (testQuestion.getTestQuestionOrder().compareTo(newOrder) <= 0 && testQuestion.getTestQuestionOrder().compareTo(oldOrder) > 0) {
                        testQuestion.setTestQuestionOrder(testQuestion.getTestQuestionOrder() - 1);
                    }
                }
            } else {
                for (ITestQuestion testQuestion : testQuestions) {
                    if (testQuestion.getTestQuestionOrder().compareTo(newOrder) >= 0 && testQuestion.getTestQuestionOrder().compareTo(oldOrder) < 0) {
                        testQuestion.setTestQuestionOrder(testQuestion.getTestQuestionOrder() + 1);
                    }
                }
            }
        }
    }

    public void delete() {
        removeQuestion();
        removeTest();
        deleteDomainObject();
    }

}

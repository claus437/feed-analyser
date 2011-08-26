package org.wooddog.servlets.actions;

import org.wooddog.dao.CompanyService;
import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.domain.Company;
import org.wooddog.servlets.PageAction;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 25-08-11
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class FindCompanyAction implements PageAction {
    public static final String PARAM_QUERY = "companyQuery";
    private CompanyService companyService;
    private List<Company> result;
    private Map<String, String[]> parameters;

    public FindCompanyAction() {
        companyService = CompanyServiceDao.getInstance();
    }

    @Override
    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }



    @Override
    public void execute() {
        List<Company> companyList;
        String companyQuery;

        if (!parameters.containsKey(PARAM_QUERY)) {
            throw new RuntimeException("required parameter \"" + PARAM_QUERY + "\" is missing");
        }

        companyQuery = parameters.get(PARAM_QUERY)[0];

        companyList = companyService.getCompanies();

        for (Company company : companyList) {
            if (company.getName().matches(companyQuery)) {
                companyList.add(company);
            }
        }
    }

    public List<Company> getResult() {
        return result;
    }
}

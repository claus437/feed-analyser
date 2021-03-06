package org.wooddog.servlets.actions;

import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.domain.Company;
import org.wooddog.servlets.PageAction;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 12-08-11
 * Time: 08:58
 * To change this template use File | Settings | File Templates.
 */
public class AddCompanyAction implements PageAction {
    private Map<String, String[]> parameters;

    @Override
    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public void execute() {
        String name;
        Company company;

        name = parameters.get("name")[0];

        company = new Company();
        company.setName(name);

        CompanyServiceDao.getInstance().storeCompany(company);
    }
}

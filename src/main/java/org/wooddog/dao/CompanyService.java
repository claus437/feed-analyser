package org.wooddog.dao;

import org.wooddog.domain.Company;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyService {
    List<Company> getCompanies();
    void storeCompany(Company company);
    Company getCompany(int companyId);
}

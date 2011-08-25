package org.wooddog.dao.mapper;

import org.wooddog.domain.Company;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public interface  CompanyMapper {
    List<Company> getCompanies();
    void storeCompany(Company company);
    Company getCompany(int id);
}

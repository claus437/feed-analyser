package org.wooddog.dao.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.Service;
import org.wooddog.dao.mapper.CompanyMapper;
import org.wooddog.domain.Company;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-07-11
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class CompanyServiceDao implements CompanyService {
    private static final CompanyServiceDao INSTANCE = new CompanyServiceDao();
    private SqlSessionFactory factory;

    private CompanyServiceDao() {
        factory = Service.getFactory();
    }

    public static CompanyServiceDao getInstance() {
        return INSTANCE;
    }

    public List<Company> getCompanies() {
        SqlSession session;
        List<Company> companies;

        session = factory.openSession();
        try {
            companies = session.getMapper(CompanyMapper.class).getCompanies();
            session.commit();
        } finally {
            session.close();
        }

        return companies;
    }

    public void storeCompany(Company company) {
        SqlSession session;

        session = factory.openSession();
        try {
            session.getMapper(CompanyMapper.class).storeCompany(company);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Company getCompany(int id) {
        SqlSession session;
        Company company;

        session = factory.openSession();
        try {
            company = session.getMapper(CompanyMapper.class).getCompany(id);
            session.commit();
        } finally {
            session.close();
        }

        return company;
    }

}

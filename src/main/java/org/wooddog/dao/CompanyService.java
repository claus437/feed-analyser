package org.wooddog.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.mapper.ChannelMapper;
import org.wooddog.dao.mapper.CompanyMapper;
import org.wooddog.domain.Channel;
import org.wooddog.domain.Company;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-07-11
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class CompanyService {
    private static final CompanyService INSTANCE = new CompanyService();
    private SqlSessionFactory factory;

    private CompanyService() {
        factory = Service.getFactory();
    }

    public static CompanyService getInstance() {
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

}

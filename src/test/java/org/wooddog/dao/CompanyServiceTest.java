package org.wooddog.dao;

import junit.framework.Assert;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.domain.Company;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.List;

public class CompanyServiceTest extends DaoTestCase {
    private CompanyService service = CompanyService.getInstance();


    @Test
    public void testGetCompanies() throws Exception {
        List<Company> companies;

        execute(DatabaseOperation.CLEAN_INSERT, "GetCompanies");

        companies = service.getCompanies();
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals(1, companies.get(0).getId());
        Assert.assertEquals("2", companies.get(0).getName());
    }
}

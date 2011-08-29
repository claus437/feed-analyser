package org.wooddog.servlets.model;

import org.wooddog.dao.CompanyService;
import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.domain.Company;

public class CompanyModel implements ModelChangeListener {
    private CompanyService companyService;
    private ModelChangeNotifier notifier;
    private int companyId;
    private Company company;
    private boolean refresh;

    public CompanyModel() {
        companyService = CompanyServiceDao.getInstance();
        notifier = new ModelChangeNotifier(this);
        notifier.addModelChangeListener(this);
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        notifier.notifyListeners(this.companyId, companyId);
        this.companyId = companyId;
    }

    public Company getCompany() {
        if (refresh) {
            company = companyService.getCompany(companyId);
            refresh = false;
        }

        return company;
    }

    public void addModelChangeListener(ModelChangeListener listener) {
        notifier.addModelChangeListener(listener);
    }

    @Override
    public void onChange(Object src, Object oldValue, Object newValue) {
        refresh = true;
    }
}

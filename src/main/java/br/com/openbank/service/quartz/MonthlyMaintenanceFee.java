package br.com.openbank.service.quartz;

import br.com.openbank.service.account.IAccountService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class MonthlyMaintenanceFee implements Job {
    @Autowired
    private IAccountService iccountService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        iccountService.maintenanceFee();
    }
}

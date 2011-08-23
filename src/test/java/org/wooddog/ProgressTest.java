package org.wooddog;

import org.junit.Test;

public class ProgressTest {
    Progress subject = new Progress();


    @Test
    public void testProgress() {
        int companySize;
        int articleSize;

        companySize = 3;

        subject.setNumberOfUnits(companySize);
        for (int i = 0; i < companySize; i++) {
            articleSize = 6;
            subject.setStepsPerUnit(articleSize);

            for (int j = 0; j < articleSize; j++) {
                subject.step();
                System.out.print(subject.getPercentDone() + " ");
            }

            System.out.println();
        }
    }
}

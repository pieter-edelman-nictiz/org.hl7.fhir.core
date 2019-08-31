package org.hl7.fhir.validation.tests;

import java.awt.Desktop;
import java.io.File;

import org.hl7.fhir.r5.conformance.ProfileComparer;
import org.hl7.fhir.r5.model.FhirPublication;
import org.hl7.fhir.r5.model.OperationOutcome;
import org.hl7.fhir.r5.model.StructureDefinition;
import org.hl7.fhir.r5.model.OperationOutcome.OperationOutcomeIssueComponent;
import org.hl7.fhir.r5.validation.ValidationEngine;
import org.hl7.fhir.validation.tests.utilities.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public class ProfileComparisonTests {

  private static final String DEF_TX = "http://tx.fhir.org";

  @Test
  public void testCurrentComparison() throws Exception {
    if (!TestUtilities.silent) 
      System.out.println("Compare US Patient Core with AU Patient Base");
    ValidationEngine ve = new ValidationEngine("hl7.fhir.core#3.0.1", DEF_TX, null, FhirPublication.R4);
    ve.loadIg("hl7.fhir.us.core#1.0.1", false);
    ve.loadIg("hl7.fhir.au.base#dev", false);


    String left = "http://hl7.org/fhir/us/core/StructureDefinition/us-core-patient";
    String right = "http://hl7.org.au/fhir/StructureDefinition/au-patient";
    String dest = TestUtilities.resourceNameToFile("comparison", "output");

    // ok now set up the comparison
    StructureDefinition sdL = ve.getContext().fetchResource(StructureDefinition.class, left);
    ProfileComparer pc = new ProfileComparer(ve.getContext());
    if (sdL == null) {
      System.out.println("Unable to locate left profile " +left);
    } else {
      StructureDefinition sdR = ve.getContext().fetchResource(StructureDefinition.class, right);
      if (sdR == null) {
        System.out.println("Unable to locate right profile " +right);
      } else {
        System.out.println("Comparing "+left+" to "+right);
        pc.compareProfiles(sdL, sdR);
        System.out.println("Generating output...");
        File htmlFile = null;
        try {
          htmlFile = new File(pc.generate(dest));
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
        }
        Desktop.getDesktop().browse(htmlFile.toURI());
        System.out.println("Done");
      }
    }
  }

//    int e = errors(op);
//    int w = warnings(op);
//    int h = hints(op);
//    if (!TestUtilities.silent) {
//      System.out.println("  .. done: "+Integer.toString(e)+" errors, "+Integer.toString(w)+" warnings, "+Integer.toString(h)+" information messages");
//      for (OperationOutcomeIssueComponent iss : op.getIssue()) {
//        System.out.println("    "+iss.getDetails().getText());
//      }
//    }
//    Assert.assertTrue(e == 0);
//    Assert.assertTrue(w == 0);
//    Assert.assertTrue(h == 0);
//  }
}

package org.hl7.fhir.r4.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.fhir.ucum.UcumException;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.formats.IParser;
import org.hl7.fhir.r4.formats.IParser.OutputStyle;
import org.hl7.fhir.r4.formats.JsonParser;
import org.hl7.fhir.r4.formats.XmlParser;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DomainResource;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.test.utils.TestingUtilities;
import org.hl7.fhir.r4.utils.EOperationOutcome;
import org.hl7.fhir.r4.utils.NarrativeGenerator;
import org.hl7.fhir.utilities.filesystem.ManagedFileAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ResourceRoundTripTests {

  @BeforeAll
  public static void setUp() throws Exception {
  }

  @Test
  @Disabled
  public void test() throws FileNotFoundException, IOException, FHIRException, EOperationOutcome, UcumException {
    Resource res = new XmlParser().parse(ManagedFileAccess.inStream(TestingUtilities.resourceNameToFile("unicode.xml")));
    new NarrativeGenerator("", "", TestingUtilities.context()).generate((DomainResource) res, null);
    new XmlParser().setOutputStyle(OutputStyle.PRETTY)
        .compose(ManagedFileAccess.outStream(TestingUtilities.resourceNameToFile("gen", "unicode.out.xml")), res);
  }

  @Test
  public void testBundle() throws FHIRException, IOException {
    // Create new Atom Feed
    Bundle feed = new Bundle();

    // Serialize Atom Feed
    IParser comp = new JsonParser();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    comp.compose(os, feed);
    os.close();
    String json = os.toString();

    // Deserialize Atom Feed
    JsonParser parser = new JsonParser();
    InputStream is = new ByteArrayInputStream(json.getBytes("UTF-8"));
    Resource result = parser.parse(is);
    if (result == null)
      throw new FHIRException("Bundle was null");
  }
}
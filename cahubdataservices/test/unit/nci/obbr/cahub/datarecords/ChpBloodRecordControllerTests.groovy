package nci.obbr.cahub.datarecords

import grails.test.*

class ChpBloodRecordControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        controller.index()
        assertEquals "list", controller.redirectArgs["action"]
    }

}

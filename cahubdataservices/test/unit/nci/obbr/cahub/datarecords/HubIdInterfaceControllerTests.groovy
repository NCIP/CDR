package nci.obbr.cahub.datarecords

import grails.test.*

class HubIdInterfaceControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        controller.index()
        assertEquals "create", controller.redirectArgs["action"]
    }

}

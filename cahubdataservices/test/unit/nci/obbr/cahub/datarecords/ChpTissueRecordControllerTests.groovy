package nci.obbr.cahub.datarecords

import grails.test.*

class ChpTissueRecordControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        //controller.index()
        //assertEquals "list", controller.redirectArgs("action")
        def model = controller.index()
        if (model) { println('Model Exist') 
        } //else { println(Null Model) }
        assert model
        assertNotZero model.chpTissueRecordInstanceList.size()
        //assertEquals "Riya1", model["chpTissueRecordInstanceList"].username 
    }
}

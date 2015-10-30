package org.klauer.soapui.demo.action;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

/**
 *
 * @author A03182
 */
public class DemoAction extends AbstractSoapUIAction {

    public DemoAction() {
        super("Nick's Demo Action", "Demonstrates an extension to SoapUI");

    }

    @Override
    public void perform(ModelItem target, Object param) {
        UISupport.showInfoMessage("Welcome to my action in project [" + target.getName() + "]");
    }

}

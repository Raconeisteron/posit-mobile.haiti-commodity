package org.hfoss.posit.android.experimental.plugin;

import java.util.ArrayList;

import org.hfoss.posit.android.experimental.api.activity.SettingsActivity;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import android.app.Activity;
import android.app.Service;
import android.util.Log;

public class FunctionPlugin extends Plugin {
		
	// Extension point in PositMain
	protected String mExtensionPoint = null;
	protected Class<Activity> mMenuActivity;
	protected String mMenuIcon;
	protected String mMenuTitle;
	protected Boolean activityReturnsResult = false;
	protected int activityResultAction = 0;
	/* BEGINS - A list of all services this funtion plug-in requires*/
	protected ArrayList<Class<Service>> mServices = new ArrayList<Class<Service>>();
	/* ENDS - A list of all services this funtion plug-in requires*/
	
	public FunctionPlugin (Activity activity, Node node) throws DOMException, ClassNotFoundException {
		mMainActivity = activity;
		
		// Perhaps this can be done more generally, rather than for each possible node
		Node aNode = null;

		for (int i = 0; i < node.getAttributes().getLength(); i++) {
			aNode = node.getAttributes().item(i);
			if (aNode.getNodeName().equals("name")) {
				name = aNode.getTextContent();
			}
			if (aNode.getNodeName().equals("type")) {
				type = aNode.getTextContent();
			}
			if (aNode.getNodeName().equals("activity")) {
				this.activity = (Class<Activity>) Class.forName(aNode.getTextContent());
			}
			if (aNode.getNodeName().equals("extensionPoint")) {
				mExtensionPoint = aNode.getTextContent();
			}
			if (aNode.getNodeName().equals("menuActivity")) {
				mMenuActivity = (Class<Activity>) Class.forName(aNode.getTextContent());
			}
			if (aNode.getNodeName().equals("menuIcon")) {
				mMenuIcon = aNode.getTextContent();
			}
			if (aNode.getNodeName().equals("menuTitle")) {
				mMenuTitle = aNode.getTextContent();
			}
			if (aNode.getNodeName().equals("activity_returns_result")) {
				activityReturnsResult = Boolean.valueOf(aNode.getTextContent());
			}
			if (aNode.getNodeName().equals("activity_result_action")) {
				activityResultAction = Integer.parseInt(aNode.getTextContent());
			}
			/* BEGINS - Function Plugin now has a preference */
			if (aNode.getNodeName().equals("preferences_xml")) {
				mPreferences = aNode.getTextContent();
				SettingsActivity.loadPluginPreferences(mMainActivity, mPreferences);
			}
			/* ENDS - Function Plugin now has a preference */
			/* BEGINS - Function Plugin now has a preference */
			if (aNode.getNodeName().equals("service")) {
				Class<Service> service = (Class<Service>) Class.forName(aNode.getTextContent());
				mServices.add(service);
			}
			/* ENDS - A list of all services this funtion plug-in requires*/
		}
		
	}
	
	public Boolean getActivityReturnsResult() {
		return activityReturnsResult;
	}

	public void setActivityReturnsResult(Boolean activityReturnsResult) {
		this.activityReturnsResult = activityReturnsResult;
	}

	public String getmExtensionPoint() {
		return mExtensionPoint;
	}

	public void setmExtensionPoint(String mExtensionPoint) {
		this.mExtensionPoint = mExtensionPoint;
	}

	public Class<Activity> getmMenuActivity() {
		return mMenuActivity;
	}

	public void setmMenuActivity(Class<Activity> mMenuActivity) {
		this.mMenuActivity = mMenuActivity;
	}

	public String getmMenuIcon() {
		return mMenuIcon;
	}

	public void setmMenuIcon(String mMenuIcon) {
		this.mMenuIcon = mMenuIcon;
	}

	public String getmMenuTitle() {
		return mMenuTitle;
	}

	public void setmMenuTitle(String mMenuTitle) {
		this.mMenuTitle = mMenuTitle;
	}
	
	public int getActivityResultAction() {
		return activityResultAction;
	}

	public void setActivityResultAction(int activityResultAction) {
		this.activityResultAction = activityResultAction;
	}
	
	/* BEGINS - A list of all services this funtion plug-in requires*/
	public ArrayList<Class<Service>> getmServices() {
		return mServices;
	}
	/* ENDS - A list of all services this funtion plug-in requires*/

	public String toString() {
		return super.toString() + " " + mExtensionPoint;
	}

}

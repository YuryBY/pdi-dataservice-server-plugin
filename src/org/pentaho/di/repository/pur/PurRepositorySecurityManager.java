package org.pentaho.di.repository.pur;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.EERoleInfo;
import org.pentaho.di.repository.EEUserInfo;
import org.pentaho.di.repository.IRole;
import org.pentaho.di.repository.IRoleSupportSecurityManager;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.platform.engine.security.userroledao.ws.UserRoleException;

public class PurRepositorySecurityManager implements IRoleSupportSecurityManager, IUserRoleListChangeListener {

	private PurRepository	repository;
	private UserRoleDelegate	userRoleDelegate;
	private static final Log logger = LogFactory.getLog(UserRoleDelegate.class);
	
  public PurRepositorySecurityManager(PurRepository repository, PurRepositoryMeta repositoryMeta, IUser user) {
		this.repository = repository;
    this.userRoleDelegate = new UserRoleDelegate(this, repositoryMeta, user, logger);
    userRoleDelegate.addUserRoleListChangeListener(this);
    this.setUserRoleDelegate(userRoleDelegate);
	}

	public UserRoleDelegate getUserRoleDelegate() {
		return userRoleDelegate;
	}

	public void setUserRoleDelegate(UserRoleDelegate userRoleDelegate) {
		this.userRoleDelegate = userRoleDelegate;
	}

	public PurRepository getRepository() {
		return repository;
	}
	
	public boolean supportsMetadata() {
		return true;
	}

	public boolean supportsRevisions() {
		return true;
	}

	public boolean supportsUsers() {
		return true;
	}

	public void delUser(ObjectId id_user) throws KettleException {
	}

	public ObjectId getUserID(String login) throws KettleException {
		return null;
	}

	public ObjectId[] getUserIDs() throws KettleException {
		return null;
	}

	public IUser loadUserInfo(String login) throws KettleException {
    // Create a UserInfo object
	  IUser user = constructUser();
	  user.setLogin(login);
    user.setName(login);
    return user;
	}

	public IUser loadUserInfo(String login, String password) throws KettleException {
    // Create a UserInfo object
    IUser user = constructUser();
    user.setLogin(login);
    user.setPassword(password);
    user.setName(login);
    return user;
	}

	public void renameUser(ObjectId id_user, String newname) throws KettleException {
	}

	public void saveUserInfo(IUser user) throws KettleException {
		userRoleDelegate.createUser(user);
	}

	public void createRole(IRole newRole) throws KettleException {
		userRoleDelegate.createRole(newRole);
	}

	public void deleteRoles(List<IRole> roles) throws KettleException {
		userRoleDelegate.deleteRoles(roles);
	}

	 public void deleteUsers(List<IUser> users) throws KettleException {
	    userRoleDelegate.deleteUsers(users);
	  }

	public IRole getRole(String name) throws KettleException {
		return userRoleDelegate.getRole(name);
	}


	public List<IRole> getRoles() throws KettleException {
		return userRoleDelegate.getRoles();
	}

	public void updateRole(IRole role) throws KettleException {
		userRoleDelegate.updateRole(role);		
	}

	public void updateUser(IUser user) throws KettleException {
		userRoleDelegate.updateUser(user);
	}
	public void delUser(String name) throws KettleException {
		userRoleDelegate.deleteUser(name);
		
	}

	public void deleteRole(String name) throws KettleException {
		userRoleDelegate.deleteRole(name);
		
	}

	public List<IUser> getUsers() throws KettleException {
		return userRoleDelegate.getUsers();
	}

	public void setRoles(List<IRole> roles) throws KettleException {
		userRoleDelegate.setRoles(roles);
		
	}

	public void setUsers(List<IUser> users) throws KettleException {
		userRoleDelegate.setUsers(users);
	}

  public IRole constructRole() throws KettleException {
    return new EERoleInfo();
  }

  public IUser constructUser() throws KettleException {
    return new EEUserInfo();
  }
  
  public void onChange() {
    try {
      userRoleDelegate.updateUserRoleInfo();
    } catch (UserRoleException e) {
      e.printStackTrace();
    }
  }
  public static Log getLogger() {
    return logger;
  }


}
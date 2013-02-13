package org.scribe.builder.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.scribe.model.*;
import org.scribe.utils.Preconditions;

public class LinkedInApi extends DefaultApi10a
{
  private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";
  
  private Set<String> scopePermissions;
      
  /**
   * Adds an scope permission parameter.
   * 
   * @param key name of the parameter   
   * 
   * @throws IllegalArgumentException if the parameter is not an OAuth parameter
   */
  public void addScopePermission(String permission)
  {
    Preconditions.checkEmptyString(permission, "Scope permission is incorrect. It can't be null or empty");
    
    if(!scopePermissions.contains(permission))
        scopePermissions.add(permission);
  }
  
  /**
   * Delete the scope permission parameters.   
   */
  public void clearScopePermission()
  {
      scopePermissions.clear();
  }
  
  @Override
  public String getAccessTokenEndpoint()
  {
    return "https://api.linkedin.com/uas/oauth/accessToken";
  }

  @Override
  public String getRequestTokenEndpoint()
  {
    String permissions = "";
    Iterator<String> it = scopePermissions.iterator();
    if(it.hasNext()) 
    {
        permissions = "?scope=" + it.next();
        while(it.hasNext())
            permissions += "+" + it.next();    
    }
      
    return "https://api.linkedin.com/uas/oauth/requestToken" + permissions;
  }
  
  @Override
  public String getAuthorizationUrl(Token requestToken)
  {
    return String.format(AUTHORIZE_URL, requestToken.getToken());
  }  
 
  public LinkedInApi()
  {
      scopePermissions = new HashSet<String>();
  }
  
  public LinkedInApi(String ... scopePermissionsParameters)
  {
      scopePermissions = new HashSet<String>(Arrays.asList(scopePermissionsParameters));      
  }
}

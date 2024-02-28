package in.aman.services;

import java.util.List;

import in.aman.bindings.App;

public interface ArService {
	
	
	public String createApplication(App app);
	
	public List<App> fetchApps(Integer userId);
	
	
}

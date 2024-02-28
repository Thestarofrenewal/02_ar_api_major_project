package in.aman.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import in.aman.bindings.App;
import in.aman.constants.AppConstant;
import in.aman.entities.AppEntity;
import in.aman.entities.UserEntity;
import in.aman.exception.SsaWebException;
import in.aman.repo.AppRepo;
import in.aman.repo.UserRepo;

@Service
public class ArServiceImpl implements ArService {

	@Autowired
	private AppRepo appRepo;

	@Autowired
	private UserRepo userRepo;

	private static final String SSA_WEB_API_URL = "https://ssa.web.app/{ssn}";

	@Override
	public String createApplication(App app) {

		try {

			WebClient webClient = WebClient.create();

			String stateName = webClient.get().uri(SSA_WEB_API_URL, app.getSsn()).retrieve().bodyToMono(String.class)
					.block();

			if (AppConstant.RI.equals(stateName)) {

				UserEntity userEntity = userRepo.findById(app.getUserId()).get();

				AppEntity entity = new AppEntity();
				BeanUtils.copyProperties(app, entity);

				entity.setUser(userEntity);

				entity = appRepo.save(entity);

				return "App created with Case num : " + entity.getCaseNum();
			}

		} catch (Exception e) {
			throw new SsaWebException(e.getMessage());
		}

		return AppConstant.INAVLID_SSN;
	}

	@Override
	public List<App> fetchApps(Integer userId) {

		UserEntity userEntity = userRepo.findById(userId).get();

		Integer roleId = userEntity.getRoleId();

		List<AppEntity> appEntities = null;

		if (1 == roleId) {
			appEntities = appRepo.fetchUserApps();
		} else {
			appEntities = appRepo.fetchCwApps(userId);
		}
		List<App> apps = new ArrayList<>();
		
		for (AppEntity entity : appEntities) {
			
			App app = new App();
			BeanUtils.copyProperties(entity, apps);
			apps.add(app);
			
		}
		return apps;
	}

}

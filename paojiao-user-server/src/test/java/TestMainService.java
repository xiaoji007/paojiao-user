public class TestMainService {

	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.active", "dev");
		System.setProperty("env", "DEV");
		System.setProperty("dev_meta", "http://test.store.paojiao.com:18080/");
		System.setProperty("apollo.cluster", "paojiao");
		System.setProperty("apollo.autoUpdateInjectedSpringProperties", "true");
		com.paojiao.user.MainService.main(args);
	}
}
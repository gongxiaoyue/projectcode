import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.dao.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;

public class test {

	private ApplicationContext ap;
	
	@Before
	public void dd() {
		ap = new ClassPathXmlApplicationContext("classpath:spring/applicationContent-dao.xml");
	}
	@Test
	public void tst() {
		TbItemCatMapper bean = (TbItemCatMapper)ap.getBean("tbItemCatMapper",TbItemCatMapper.class);
		TbItemCatExample ex = new TbItemCatExample();
		Criteria createCriteria = ex.createCriteria();
		long l=1;
		createCriteria.andParentIdEqualTo(l);
		TbItemCat selectByPrimaryKey = bean.selectByPrimaryKey(l);
		System.out.println(selectByPrimaryKey.getName());
		List<TbItemCat> selectByExample = bean.selectByExample(ex);
		for (TbItemCat tbItemCat : selectByExample) {
			System.out.println(tbItemCat.getName());
		}
	}
}

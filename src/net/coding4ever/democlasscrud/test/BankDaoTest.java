package net.coding4ever.democlasscrud.test;

import java.util.ArrayList;
import java.util.List;

import model.Bank;

import dao.api.IBankDao;
import dao.impl.BankDao;
import dao.impl.DbConnection;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;

public class BankDaoTest extends AndroidTestCase {
	
	private IBankDao oDao;
	
	@Override
	protected void setUp() throws Exception {
		// create database
		oDao = new BankDao(mContext);
				
		// inisialisasi data
		initData();
	}
	
	@Override
	protected void tearDown() throws Exception {
		@SuppressWarnings("unused")
		Boolean result = mContext.deleteDatabase(DbConnection.DATABASE_NAME);
	}
	
	@Suppress
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}
	
	private void initData()
	{				
		List<Bank> oList = new ArrayList<Bank>();
		
		Bank bank1 = new Bank();
		bank1.setNamaBank("BNI");
		
		Bank bank2 = new Bank();
		bank2.setNamaBank("BRI");
		
		Bank bank3 = new Bank();
		bank3.setNamaBank("Mandiri");
		
		oList.add(bank1);
		oList.add(bank2);
		oList.add(bank3);
		
		for (Bank bank : oList) {
			@SuppressWarnings("unused")
			int result = oDao.save(bank);
		}
	}
	
	public void testSave()
	{
		Bank bank = new Bank();
		bank.setNamaBank("Muamalat");
		
		int result = oDao.save(bank);
		assertTrue(result != -1);
	}
	
	public void testUpdate()
	{
		int bankID = 2;
		
		// cek bank sebelum diupdate
		Bank bankBeforeUpdate = oDao.getByID(bankID);
		assertEquals("BRI", bankBeforeUpdate.getNamaBank());
		
		// update data bank
		Bank bank = new Bank();
		bank.setBankID(bankID);
		bank.setNamaBank("Bank Rakyat Indonesia");
		
		int result = oDao.update(bank);
		assertEquals(1, result);
		
		// cek bank setelah diupdate
		Bank bankAfterUpdate = oDao.getByID(bankID);
		assertEquals("Bank Rakyat Indonesia", bankAfterUpdate.getNamaBank());
	}
	
	public void testDelete()
	{
		int bankID = 2;
		
		// cek bank sebelum dihapus
		Bank bankBeforeDelete = oDao.getByID(bankID);
		assertEquals("BRI", bankBeforeDelete.getNamaBank());
		
		// hapus bank
		Bank bank = new Bank();
		bank.setBankID(bankID);
		
		int result = oDao.delete(bank);
		assertEquals(1, result);
		
		// cek bank setelah dihapus
		Bank bankAfterDelete = oDao.getByID(bankID);
		assertNull(bankAfterDelete);
	}
	
	public void testGetByID()
	{
		int bankID = 2;
		
		Bank bank = oDao.getByID(bankID);
		
		assertNotNull(bank);
		assertEquals("Bank Rakyat Indonesia", bank.getNamaBank());
	}
	
	public void testGetAll()
	{
		// load object bank
		List<Bank> oList = oDao.getAll();
		
		// harusnya ada tiga objek
		assertEquals(3, oList.size());
		
		// cek objek bank terakhir
		Bank bank = oList.get(2);
		
		assertNotNull(bank);
		assertEquals("Mandiri", bank.getNamaBank());
	}

}

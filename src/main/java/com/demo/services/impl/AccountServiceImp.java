package com.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entities.Account;
import com.demo.entities.FoodMenu;
import com.demo.entities.Role;
import com.demo.repositories.AccountRepository;
import com.demo.services.AccountService;

@Service
public class AccountServiceImp implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean save(Account account) {
		try {
			account = accountRepository.save(account);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Account find(int id) {
		// TODO Auto-generated method stub
		return accountRepository.findById(id).get();
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		try {
			Account account = find(id);
			accountRepository.delete(account);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getpassword(int id) {
		Account account = find(id);
		return account.getPassword();
	}

	@Override
	public Account findbyusername(String username) {
		try {
			Account account = accountRepository.findbyusername(username);
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Account login(String username, String password) {
		try {
			return accountRepository.login(username, password, true);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Account findbyemail(String email) {
		try {

			return accountRepository.findbyemail(email);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findbyusername(username);
		if (account == null) {
			throw new UsernameNotFoundException("username not found");
		}
		if (!account.isStatus()) {
			throw new UsernameNotFoundException("this user is blocked");
		} else {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (Role role : account.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			}
			return new User(username, account.getPassword(), authorities);
		}

	}

	@Override
	public boolean checkexistence(String username) {
		if (accountRepository.findbyusername(username) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkphone(String phone) {
		if (accountRepository.findbyphone(phone) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkemail(String email) {
		if (accountRepository.findbyemail(email) != null) {
			return true;
		}
		return false;
	}

	@Override

	public Page<Account> findAllByRole(int roleId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<Account> accounts = accountRepository.findAll(pageable);
		List<Account> accountsByRole = new ArrayList<>();

		for (Account account : accounts.getContent()) {
			Set<Role> roles = account.getRoles();
			for (Role role : roles) {
				if (role.getId() == roleId) {
					accountsByRole.add(account);
					break; // Break out of inner loop once role is found
				}
			}
		}

		return new PageImpl<>(accountsByRole, pageable, accountsByRole.size());
	}

	public List<Account> findAllByRoles(int n) {
		List<Account> accounts = accountRepository.findallaccount();
		List<Account> accountsByRole = new ArrayList<>();
		for (Account account : accounts) {
			Set<Role> roles = account.getRoles();
			for (Role role : roles) {
				if (role.getId() == n) {
					accountsByRole.add(account);
				}
			}
		}
		return accountsByRole;
	}

	public String getpassword(String username) {
		Account account = accountRepository.findbyusername(username);
		return account.getPassword();
	}

	@Override
	public boolean isLoggedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Account> findAccount1(String kw, int id) {
		return accountRepository.searchAccounts(kw, id);
	}

	@Override

//	public Page<Account> findAccount(String kw, int id, int pageNo, int pageSize) {
//		List<Account> list= this.findAccount1(kw,id);
//		
//	    if (kw != null && !kw.isEmpty()) {
//	        // If keyword is not null or empty, perform search with the keyword
//	        list = this.findAccount1(kw, id);
//	    } else {
//	        // If keyword is null or empty, fetch all accounts by role without filtering by keyword
//	        list = findAllByRoles(id);
//	    }
//		
//		
//		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
//		
//		Integer start = (int) pageable.getOffset();
//		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
//		list = list.subList(start, end);
//		return new PageImpl<Account>(list, pageable, this.findAccount1(kw,id).size());
//	}

	public Integer paidForMoviebyAccountId(int id) {
		Integer sum = accountRepository.sumBookingPricesByAccountId(id);
		return sum != null ? sum : 0;
	}

	@Override
	public Integer sumFoodPricesByAccountId(int id) {
		Integer sum = accountRepository.sumFoodPricesByAccountId(id);
		return sum != null ? sum : 0;
	}

	@Override
	public Integer countAccountsWithRoleId(int id) {
		Integer sum = accountRepository.countAccountsWithRoleId(id);
		return sum != null ? sum : 0;
	}

	@Override
	public List<Account> top5paid() {
		// TODO Auto-generated method stub
		return accountRepository.findTop5AccountsByTotalPriceWithLimit();
	}

	@Override
	public Integer allPaidbyAccountId(int accountId) {
		Integer sum = accountRepository.getTotalPriceByAccountId(accountId);
		return sum != null ? sum : 0;
	}

	@Override
	public List<Account> findAccount(String kw, int id) {
		return accountRepository.searchAccounts(kw, id);
	}

}

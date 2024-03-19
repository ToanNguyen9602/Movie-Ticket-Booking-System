package com.demo.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.demo.entities.FoodMenu;
import com.demo.repositories.FoodRepository;
import com.demo.services.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Override
	public boolean save(FoodMenu food) {
		try {

			if (foodRepository.existsByName(food.getName())) {
				
				return false;
			} else {
				
				foodRepository.save(food);
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

	
	

	@Override
	public FoodMenu find(int id) {
		// TODO Auto-generated method stub
		return foodRepository.findById(id).get();
	}
	
	@Override
	public boolean save2(FoodMenu food) {
		try {
				
				foodRepository.save(food);
				return true;
			}

		 catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<FoodMenu> SearchByFoodName1(String kw) {
		// TODO Auto-generated method stub
		return foodRepository.SearchByFoodName(kw);
	}

	@Override
	public Page<FoodMenu> SearchByFoodName(String kw, int pageNo, int pageSize) {
		List list = this.SearchByFoodName1(kw);
		
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<FoodMenu>(list, pageable, this.SearchByFoodName1(kw).size());
	}

	

	@Override
	public List<FoodMenu> findAll_ListFood() {
		return foodRepository.findAll_ListFood();
	}




	@Override
	public Page<FoodMenu> findAll_ListFoodpagin(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		return foodRepository.findAll_ListFoodpagin(pageable);
	}

	

	

	




}

package com.rl.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rl.dao.PokerDao;
import com.rl.model.Player;
import com.rl.model.Poker;
import com.rl.utils.HBUtils;


public class PokerDaoImpl implements PokerDao{
	
	@Override
	public List<Poker> getAllPoker() {
		Session session=HBUtils.getSession();
		Query query=	session.createQuery("from Poker");
		return query.list();
	}

	
	

}

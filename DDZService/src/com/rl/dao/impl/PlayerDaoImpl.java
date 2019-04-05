package com.rl.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rl.dao.PlayerDao;
import com.rl.model.Player;
import com.rl.utils.HBUtils;

public class PlayerDaoImpl implements PlayerDao {

	@Override
	public List<Player> validataLogin(Player player) {
		// TODO Auto-generated method stub
		Session session=HBUtils.getSession();
		Query query=	session.createQuery("from Player p where p.name=? and p.password=?");
		query.setParameter(0, player.getName());
		query.setParameter(1, player.getPassword());
		return query.list();
	}
}

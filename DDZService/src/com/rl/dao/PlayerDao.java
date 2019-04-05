package com.rl.dao;

import java.util.List;

import com.rl.model.Player;

public interface PlayerDao {
	public List<Player> validataLogin(Player player);
}

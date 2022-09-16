/*
 * Copyright (C) 2012-2021 Frank Baumann
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.chujian.dungeonsByCJ.reward;

import com.chujian.dungeonsByCJ.api.Reward;
import com.chujian.dungeonsByCJ.config.DMessage;
import com.chujian.dungeonsByCJ.util.commons.chat.MessageUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class MoneyReward implements Reward {
    
    private Economy econ;

    private double money;

    public MoneyReward(Economy econ) {
        this.econ = econ;
    }

    /**
     * @return the money
     */
    public double getMoney() {
        return money;
    }

    /**
     * @param money the money to add
     */
    public void addMoney(double money) {
        this.money += money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public void giveTo(Player player) {
        if (econ == null || money == 0) {
            return;
        }

        econ.depositPlayer(player, money);
        MessageUtil.sendMessage(player, DMessage.REWARD_GENERAL.getMessage(econ.format(money)));
    }

}

package com.devin12422.tweaks;


public interface EmotePlayerInterface {

	void playEmote(Emote emote);

	Emote getEmote();

	int getLastUpdated();

	void resetLastUpdated();
}
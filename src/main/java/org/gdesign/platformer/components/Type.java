package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;

public class Type extends BaseComponent {
	
	private String t;
	
	public Type(String type) {
		t = type.toUpperCase();
	}

	@Override
	public String toString() {
		return t.toUpperCase();
	}
}

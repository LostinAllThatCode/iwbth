------------------------------------------------------------------------------------------
-- #####################################################################################--
-- PlayerController component script for org.gdesign.game engine
-- Documentation link : http://www.whatever.com
--
-- GLOBAL VARS AND FUNCTIONS | !DO NOT CHANGE!
	entity			= nil
	
	function init(e)
		entity = e
		if entity == nil then error("FATAL ERROR.") end
	end
	
	function getComponent(sName)
		if entity == nil then error("FATAL ERROR.") end
		clazz = luajava.bindClass("org.gdesign.platformer.components."..sName)
		return entity:getComponent(clazz)
	end
-- #####################################################################################--
------------------------------------------------------------------------------------------


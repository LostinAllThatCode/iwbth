-- #####################################################################################--
-- PlayerController component script for org.gdesign.game engine
-- Documentation link : http://www.whatever.com
--
-- GLOBAL VARS AND FUNCTIONS | !DO NOT CHANGE!
	jClassloader 	= "org.gdesign.game.components."
	jLua			= nil
	entity			= nil
	
	function update(jSelf,jEntity)
		entity 		= jEntity
		jLua 		= jSelf
		if entity == nil or jLua == nil then error("FATAL ERROR.") end
	end
	
	function getComponent(sName)
		if entity == nil or jLua == nil then error("FATAL ERROR.") end
		return entity:getComponent(jLua:getClazz(jClassloader..sName))
	end
-- #####################################################################################--

-- Preinitialized values to handle player input
isGrounded = false
dVelY 		= 0f
dVelX		= 0f
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= 1f
friction	= 0.2f
maxJumps 	= 1
maxVelY		= 8f
maxVelX		= 6f


boostSpeed	= .8f
normalSpeed	= .5f
turnMul 	= 4f

-- Meat boy kind of movement implementation
function doBehaviour(velX, velY)
	dVelX = velX
	dVelY = velY

	
	if keyJump then 
		dVelY = maxVelY
		return dVelX,dVelY
	end
	
	if not walk() then dVelX = 0f end
	
	return dVelX,dVelY
end

function walkAccel()
	if keyShoot then return boostSpeed end
	return normalSpeed
end

function walk()
	sign = 0
	if keyRight then 
		sign = 1
	elseif keyLeft then 
		sign = -1 
	else 
		return false
	end
		
	curSign = math.sign(dVelX)
	v = walkAccel()
	
	if curSign ~= 0 and curSign ~= sign then v = v * turnMul end
		
	dVelX = dVelX + (v * sign)
	return true
end

function airControls()
	if dVelY > 0 and not keyJump then dVelY = 0f end
	walk()
end

function math.sign(x)
   if x<0 then
     return -1
   elseif x>0 then
     return 1
   else
     return 0
   end
end

speed = 3
dir = 0
dt = 0
oldDir = 0

body = nil


function init(direction, movementSpeed)
	dir = direction
	speed = movementSpeed
end

function doBehaviour()	
	dt = dt + _world:getDelta()
	
	physics 	= _self:getComponent(component("Physics"))
	anim		= _self:getComponent(component("Animatable"))
	
	if (body == nil) then body = physics:getBody() end
	vecVel	= body:getLinearVelocity()
	
	vecVel.x = speed * dir
	if (dt >= 1) then 
		vecVel.y = 6
		dt = 0
	end
	physics:getBody():setLinearVelocity(vecVel)
	
	anim:setCurrentAnimation("RUNNING")
	if (dir == 1) then anim:flip(false,false) end
	if (dir == -1) then anim:flip(true,false) end
end


function beginCollision(target)
	if (type(target) == "userdata") then --Entity collision	
		if (target:getComponent(component("Physics")):getCategory() == CONST.CATEGORY_PLAYER) then target:destroy() end
	elseif (type(target) == "number") then --Non-Entity collsion. Check with CONST.CATEGORY_* to determine collision
	
	end
end

function endCollision(target)
	if (type(target) == "userdata") then --Entity collision	
		
	elseif (type(target) == "number") then --Non-Entity collsion. Check with CONST.CATEGORY_* to determine collision
	
	end
end
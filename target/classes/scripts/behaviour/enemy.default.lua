speed = 3
dir = 1
dt = 0
oldDir = 0

body = nil


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
	if (target:getComponent(component("Physics")):getCategory() == CONST.CATEGORY_PLAYER) then target:destroy() end
	--[[
	if (body == nil) then body =  _self:getComponent(component("Physics")):getBody() end
	vec = body:getLinearVelocity()
	dir = dir * -1
	--]]
end

function endCollision(target)
end
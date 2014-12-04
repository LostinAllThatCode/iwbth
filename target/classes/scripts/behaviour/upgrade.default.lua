delta = 0

radius = 1
originX = 0
originY = 0
angle = 0
speed = .1
started = false

function doBehaviour()
	physics = _self:getComponent(component("Physics"))
	
	if not started then 
		originX = physics.body:getPosition().x
		originY = physics.body:getPosition().y
		started = true
	end
	
	if started then
		angle = angle + speed
		if angle == 1 then angle = 0 end
		x = math.sin(angle) * radius
		y = math.cos(angle) * radius
		
		originX = originX + speed/100;
		
		physics.body:setTransform(originX + x, originY + y, 0);
	end
end


function beginCollision(target)
	_self:destroy()
	
	pos = target:getComponent(component("Position"))
	
	target:removeComponent(component("Animatable"))
	newAnim = luajava.new(component("Animatable"),"textures/entity/player/mario.png")
	target:addComponent(newAnim)
	
	--target:addComponent(newPhy)
	
	behav = target:getComponent(component("Behaviour"))
	behav:setScript("scripts/control/mario.lua")
	
end

function endCollision(target)
	--_self:destroy()
end
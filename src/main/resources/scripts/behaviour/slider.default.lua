radius = 2
angle = 0
speed = .05

function doBehaviour()
	physics = _self:getComponent(component("Physics"))
	spos = physics:getBody():getPosition()

	angle = angle + speed
	if angle == 1 then angle = 0 end
	x = math.sin(angle) * radius
	y = math.cos(angle) * radius
	
	physics.body:setLinearVelocity(x,y)
	
end


function beginCollision(target)
	--target:getComponent(component("Physics")):getBody():getFixtureList():get(0):setFriction(1);
end

function endCollision(target)
	--target:getComponent(component("Physics")):getBody():getFixtureList():get(0):setFriction(0);
end

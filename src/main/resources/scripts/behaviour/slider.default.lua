radius = 2
angle = 0
speed = .05
time = 0
starttime = 0
started = false

function init(t)
	starttime = t
end

function doBehaviour()
	time = time + GDX.graphics:getDeltaTime()
	if (time >= starttime) then started = true end
	if (started) then 
		physics = _self:getComponent(component("Physics"))
		spos = physics:getBody():getPosition()
	
		angle = angle + speed
		if angle == 1 then angle = 0 end
		x = math.sin(angle) * radius
		y = math.cos(angle) * radius
		
		physics.body:setLinearVelocity(x,y)
	end
	
end


function beginCollision(target)
	--target:getComponent(component("Physics")):getBody():getFixtureList():get(0):setFriction(1);
end

function endCollision(target)
	--target:getComponent(component("Physics")):getBody():getFixtureList():get(0):setFriction(0);
end

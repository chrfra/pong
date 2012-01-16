varying vec3 lightDir,normal;

void main()
{
	normal = normalize(gl_NormalMatrix * gl_Normal);

	lightDir = normalize(vec3(gl_LightSource[0].position));
	gl_TexCoord[0] = gl_MultiTexCoord0;

	gl_Position = ftransform();
}
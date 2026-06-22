// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class кит_Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "кит_converted"), "main");
	private final ModelPart bone;

	public кит_Converted(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-75, -62).addBox(-26.0F, -43.0F, -30.0F, 25.0F, 44.0F, 76.0F, new CubeDeformation(0.0F))
		.texOffs(-57, -61).addBox(-43.0F, -23.0F, -30.0F, 17.0F, 24.0F, 75.0F, new CubeDeformation(0.0F))
		.texOffs(-53, -57).addBox(-60.0F, -8.0F, -28.0F, 17.0F, 9.0F, 71.0F, new CubeDeformation(0.0F))
		.texOffs(-45, -36).addBox(-81.0F, -66.0F, -17.0F, 34.0F, 36.0F, 50.0F, new CubeDeformation(0.0F))
		.texOffs(-12, -7).addBox(-82.0F, -90.0F, -2.0F, 14.0F, 25.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(-12, -7).addBox(38.0F, -49.0F, -33.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(-12, -7).addBox(38.0F, -49.0F, 29.0F, 8.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(-12, -7).addBox(-102.0F, -65.0F, -2.0F, 21.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(-27, -55).addBox(29.0F, -21.0F, -32.0F, 40.0F, 22.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(-51, -49).addBox(-1.0F, -71.0F, -28.0F, 70.0F, 72.0F, 74.0F, new CubeDeformation(0.0F))
		.texOffs(0, 2).addBox(-1.0F, -19.0F, -5.0F, 17.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-44, -48).addBox(-3.0F, -30.0F, -29.0F, 17.0F, 28.0F, 62.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-74.0F, -1.0F, 6.0F, 0.0F, 0.0F, 0.2618F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}